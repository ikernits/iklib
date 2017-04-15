package org.ikernits.lib.spring.process;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.ikernits.lib.common.io.stream.StreamCopyRunnable;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class ProcessExecutionServiceImpl implements ProcessExecutionService, InitializingBean, DisposableBean {

    private static final int IO_BUFFER_SIZE = 64 * 1024;
    private static final int IO_WAIT_MILLIS = 1000;
    private static final int KILL_WAIT_MILLIS = 1000;
    private static final Logger log = Logger.getLogger(ProcessExecutionServiceImpl.class);

    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactoryBuilder()
                .setDaemon(true)
                .build()
        );
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdownNow();
    }

    private class ExecutionCallable implements Callable<ExecutionResult> {
        private final ExecutionConfig config;

        public ExecutionCallable(ExecutionConfig config) {
            this.config = config;
        }

        private void waitIoFutureAndCloseStream(String name, Future<?> future, Closeable stream) {
            try {
                future.get(IO_WAIT_MILLIS, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                log.warn("stream IO: " + name + "does not stop normally after process end");
            }

            IOUtils.closeQuietly(stream);

            try {
                future.get(IO_WAIT_MILLIS, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                //ignored
            } catch (TimeoutException e) {
                future.cancel(true);
            }
        }

        private ExecutionResult callImpl() {
            List<String> command = new ArrayList<>();
            command.add(config.getExecutablePath());
            command.addAll(config.getParameters());

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            if (config.getDirectoryPath() != null) {
                processBuilder.directory(new File(config.getDirectoryPath()));
            }

            Process process;
            final long startTime = System.currentTimeMillis();
            try {
                process = processBuilder.start();
            } catch (IOException e) {
                return new ExecutionResult(config,
                    startTime, startTime, null, e,
                    IoResult.empty(), IoResult.empty(), IoResult.empty());
            }

            final Future<?> stdinFuture;
            final StreamCopyRunnable stdinIoCopyRunnable;
            if (config.getStdin() == null) {
                stdinIoCopyRunnable = null;
                stdinFuture = Futures.immediateFuture(null);
            } else {
                stdinIoCopyRunnable = new StreamCopyRunnable(
                    "process-executor-" + config.getName() + "-stdin",
                    config.getStdin(),
                    process.getOutputStream(),
                    config.getStdinLimit(),
                    IO_BUFFER_SIZE
                );
                stdinFuture = executorService.submit(stdinIoCopyRunnable);
            }

            final StreamCopyRunnable stdoutIoCopyRunnable = new StreamCopyRunnable(
                "process-executor-" + config.getName() + "-stdout",
                process.getInputStream(),
                config.getStdout(),
                config.getStdoutLimit(),
                IO_BUFFER_SIZE
            );
            final Future<?> stdoutFuture = executorService.submit(stdoutIoCopyRunnable);

            final StreamCopyRunnable stderrIoCopyRunnable = new StreamCopyRunnable(
                "process-executor-" + config.getName() + "-stderr",
                process.getErrorStream(),
                config.getStderr(),
                config.getStderrLimit(),
                IO_BUFFER_SIZE
            );
            final Future<?> stderrFuture = executorService.submit(stderrIoCopyRunnable);

            Throwable error = null;
            try {
                if (config.getTimeout() <= 0) {
                    process.waitFor();
                } else {
                    process.waitFor(config.getTimeout(), config.getTimeoutTimeUnit());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                error = e;
            }

            final Integer exitCode;
            if (!process.isAlive()) {
                exitCode = process.exitValue();
            } else {
                if (config.isKillAllowed()) {
                    log.warn("process is destroyed: '" + config.getExecutablePath() + "'");
                    process.destroy();

                    //loop is required because interrupt and happen in several cases
                    //future.cancel, executor shutdown
                    final long killWaitStartMillis = System.currentTimeMillis();
                    for (int i = 0; i < 10; ++i) {
                        try {
                            process.waitFor(KILL_WAIT_MILLIS, TimeUnit.MILLISECONDS);
                            if (System.currentTimeMillis() - killWaitStartMillis > KILL_WAIT_MILLIS) {
                                break;
                            }
                        } catch (InterruptedException e) {
                            log.warn("interrupted during process kill, ignored" + i);
                        }
                    }

                    if (!process.isAlive()) {
                        log.warn("process was destroyed normally");
                    } else {
                        log.warn("process is forcibly destroyed: '" + config.getExecutablePath() + "'");
                        process.destroyForcibly();
                    }
                }
                if (error == null) {
                    error = new TimeoutException();
                }
                exitCode = null;
            }

            final long endTime = System.currentTimeMillis();

            waitIoFutureAndCloseStream("stdin", stdinFuture, process.getOutputStream());
            waitIoFutureAndCloseStream("stdout", stdoutFuture, process.getInputStream());
            waitIoFutureAndCloseStream("stderr", stderrFuture, process.getErrorStream());

            return new ExecutionResult(config, startTime, endTime, exitCode,
                error,
                stdinIoCopyRunnable != null ? stdinIoCopyRunnable.getIoResult() : IoResult.empty(),
                stdoutIoCopyRunnable.getIoResult(),
                stderrIoCopyRunnable.getIoResult()
            );
        }

        @Override
        public ExecutionResult call() {
            final String threadName = Thread.currentThread().getName();
            Thread.currentThread().setName("process-executor-" + config.getName() + "-main");
            try {
                return callImpl();
            } finally {
                Thread.currentThread().setName(threadName);
            }
        }
    }

    @Override
    public Future<ExecutionResult> executeAsync(ExecutionConfig config) {
        return executorService.submit(new ExecutionCallable(config));
    }

    @Override
    public ExecutionResult execute(ExecutionConfig config) {
        return new ExecutionCallable(config).call();
    }


}
