package org.ikernits.lib.common.io.daemon;

import org.ikernits.lib.common.io.IoStreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkState;

public class IoDaemon {
    private Process process = null;
    private final String name;
    private final Class<? extends IoStreamSource> ioSourceClass;

    public IoDaemon(String name, Class<? extends IoStreamSource> ioSourceClass) {
        this.name = name;
        this.ioSourceClass = ioSourceClass;
    }

    public InputStream getInputStream() {
        return process.getInputStream();
    }

    public OutputStream getOutputStream() {
        return process.getOutputStream();
    }

    public void start() {
        checkState(process == null, "Already started");

        String javaHome = System.getProperty("java.home");
        String javaPath = javaHome + "/bin/java";

        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        String classPath = Stream.of(urlClassLoader.getURLs())
            .map(URL::getFile)
            .collect(Collectors.joining(":"));

        try {
            process = new ProcessBuilder()
                .command(
                    javaPath,
                    "-cp", classPath,
                    IoDaemonMain.class.getName(),
                    ioSourceClass.getName(),
                    name)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start IO daemon for class: '" + ioSourceClass.getName() + "'", e);
        }
    }

    private boolean waitForTermination(long timeout, TimeUnit timeUnit) {
        try {
            return process.waitFor(timeout, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public boolean terminate(long timeout, TimeUnit timeUnit) {
        process.destroy();
        if (waitForTermination(timeout, timeUnit)) {
            return true;
        } else {
            process.destroyForcibly();
            return waitForTermination(timeout, timeUnit);
        }
    }

    public int getExitValue() {
        return process.exitValue();
    }

    public boolean isActive() {
        return process.isAlive();
    }
}
