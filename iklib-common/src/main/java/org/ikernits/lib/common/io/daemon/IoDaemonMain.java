package org.ikernits.lib.common.io.daemon;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.ikernits.lib.common.Log4jUtils;
import org.ikernits.lib.common.io.IoStreamSource;
import org.ikernits.lib.common.io.stream.StreamCopyRunnable;

import java.lang.reflect.InvocationTargetException;

public class IoDaemonMain {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {
        final String ioClassName = args[0];
        final String daemonName = args[1];
        Thread.currentThread().setName("iodaemon-main-" + daemonName);

        Log4jUtils.configureWithDefaults(System.err);
        Logger log = Logger.getLogger(IoDaemonMain.class);
        log.info("IO Daemon started for class: '" + ioClassName + "'");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Thread.currentThread().setName("iodaemon-term-" + daemonName);
            log.info("IO Daemon terminated for class: '" + ioClassName + "'");
        }));

        final IoStreamSource streamSource;
        try {
            Object instance = Class.forName(ioClassName).getConstructor().newInstance();
            streamSource = (IoStreamSource) instance;
        } catch (Exception e) {
            LogLog.error("IO Daemon: failed to create stream source", e);
            System.exit(1);
            return;
        }

        try {
            StreamCopyRunnable inOutStreamCopy = new StreamCopyRunnable(
                "iodaemon-io-copy-" + daemonName,
                streamSource.getInputStream(),
                System.out,
                -1,
                1024 * 1024
            );
            Thread inOutThread = new Thread(inOutStreamCopy);
            inOutThread.setDaemon(true);

            StreamCopyRunnable outInStreamCopy = new StreamCopyRunnable(
                "iodaemon-oi-copy-" + daemonName,
                System.in,
                streamSource.getOutputStream(),
                -1,
                1024 * 1024
            );
            Thread outInThread = new Thread(outInStreamCopy);
            outInThread.setDaemon(true);

            inOutThread.start();
            outInThread.start();

            while (inOutThread.isAlive() && outInThread.isAlive()) {
                Thread.sleep(100);
            }

            if (inOutStreamCopy.getIoResult().getError() != null) {
                System.exit(2);
            }

            if (outInStreamCopy.getIoResult().getError() != null) {
                System.exit(3);
            }

            System.exit(0);
        } catch (Exception e) {
            LogLog.error("IO Daemon: failed to run stream IO", e);
            System.exit(127);
        }
    }
}
