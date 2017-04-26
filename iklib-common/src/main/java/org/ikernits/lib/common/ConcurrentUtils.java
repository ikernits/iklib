package org.ikernits.lib.common;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;

public class ConcurrentUtils {
    public static <T> T performLocked(Lock lock, Callable<T> action) {
        lock.lock();
        try {
            return action.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void performLocked(Lock lock, Runnable action) {
        lock.lock();
        try {
            action.run();
        } finally {
            lock.unlock();
        }
    }

    public interface ServiceThread {
        long getMillisSinceLastUpdate();

        void start();

        boolean isAlive();

        void shutdown();

        void awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException;
    }

    public interface ServiceThreadTask {

        default void beforeRun() throws Exception {

        }

        boolean perform() throws Exception;

        default void afterRun() throws  Exception {

        }
    }

    public static ServiceThread createServiceThread(String name, ServiceThreadTask task) {
        return new ServiceThreadImpl(name, task);
    }

    private static class ServiceThreadImpl extends Thread implements ServiceThread {
        private volatile long lastUpdateMillis = System.currentTimeMillis();
        private volatile boolean enabled = true;
        private final CompletableFuture<Void> completionFuture = new CompletableFuture<>();
        private final ServiceThreadTask task;

        public ServiceThreadImpl(String name, ServiceThreadTask task) {
            this.task = task;
            setDaemon(true);
            if (name != null) {
                setName(name);
            }
        }

        protected void markAlive() {
            lastUpdateMillis = System.currentTimeMillis();
        }

        @Override
        public final void run() {
            try {
                task.beforeRun();
                while (enabled) {
                    try {
                        if (!task.perform()) {
                            break;
                        }
                        markAlive();
                    } catch (InterruptedException e) {
                        //ignored
                    }
                }
            } catch (Throwable e) {
                completionFuture.completeExceptionally(e);
            }

            try {
                task.afterRun();
                completionFuture.complete(null);
            } catch (Throwable e) {
                if (!completionFuture.isDone()) {
                    completionFuture.completeExceptionally(e);
                }
            }
        }

        public long getMillisSinceLastUpdate() {
            return System.currentTimeMillis() - lastUpdateMillis;
        }

        public void shutdown() {
            enabled = false;
            interrupt();
        }

        public void awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            completionFuture.get(timeout, timeUnit);
        }
    }
}
