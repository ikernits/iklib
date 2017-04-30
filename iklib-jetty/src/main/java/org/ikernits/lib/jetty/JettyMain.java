package org.ikernits.lib.jetty;

import org.apache.log4j.helpers.LogLog;
import org.ikernits.lib.common.Log4jUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JettyMain {

    private volatile ClassPathXmlApplicationContext context = null;

    public void startServer() {
        context = new ClassPathXmlApplicationContext("classpath:META-INF/spring-context-jetty.xml");
        JettyServerLifecycleService jettyServerService = context.getBean("jettyServerLifecycleService", JettyServerLifecycleService.class);
        jettyServerService.startServer();
    }

    public void stopServer() {
        if (context != null) {
            try {
                context.destroy();
            } catch (Exception ex) {
                LogLog.error("error stopping server", ex);
            }
        }
    }

    public void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::stopServer));
    }

    public void run() {
        setupShutdownHook();
        startServer();
    }

    public static void main(String[] args) throws Exception {
        Log4jUtils.configureIfRequired();
        new JettyMain().run();
    }
}
