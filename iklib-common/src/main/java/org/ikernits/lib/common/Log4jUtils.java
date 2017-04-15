package org.ikernits.lib.common;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

public class Log4jUtils {

    public static final String DEFAULT_PATTERN = "%d [%t] %p %c %m%n";
    private static final String CONFIG_CLASSPATH = "META-INF/log4j.properties";
    private static final Logger log = Logger.getLogger(Log4jUtils.class);

    public static final Appender emptyAppender = new AppenderSkeleton() {
        @Override
        protected void append(LoggingEvent event) {
        }

        @Override
        public void close() {
        }

        @Override
        public boolean requiresLayout() {
            return false;
        }
    };

    public static void configureWithDefaults() {
        Logger.getRootLogger().removeAllAppenders();
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout(DEFAULT_PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);
        log.info("log4j uses default configuration");
    }

    public static void configureWithDefaults(OutputStream out) {
        Logger.getRootLogger().removeAllAppenders();
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout(DEFAULT_PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        console.setWriter(new OutputStreamWriter(out));
        Logger.getRootLogger().addAppender(console);
        log.info("log4j uses default configuration with specific output");
    }


    public static void configureFromFile(File propertyFile) {
        try (InputStream properties = new FileInputStream(propertyFile)) {
            PropertyConfigurator.configure(properties);
            log.info("log4j uses configuration from '" + propertyFile.getAbsolutePath() + "'");
        } catch (IOException e) {
            LogLog.warn("failed to load log4j configuration from property file", e);
            throw new RuntimeException("log4j configuration failed", e);
        }
    }

    public static void configureFromClasspath(String classpath) {
        try(InputStream properties = ClassLoader.getSystemResourceAsStream(classpath)) {
            PropertyConfigurator.configure(properties);
            log.info("loaded log4j configuration from classpath: '" + classpath + "'");
        } catch (IOException e) {
            LogLog.warn("failed to load log4j configuration from classpath: '" + classpath + "'", e);
            throw new RuntimeException("log4j configuration failed", e);
        }
    }

    public static void configureWithEmpty() {
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(emptyAppender);
    }

    public static void configureIfRequired() {
        if (Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
            log.info("log4j uses startup configuration");
            return;
        }
        if (ClassLoader.getSystemResource(CONFIG_CLASSPATH) != null) {
            try {
                configureFromClasspath(CONFIG_CLASSPATH);
                return;
            } catch (Exception ex) {
                LogLog.warn("failed to configure log4j with classpath resource '" + CONFIG_CLASSPATH + "'");
            }
        }

        configureWithDefaults();
    }
}
