package org.ikernits.lib.jetty;

import org.apache.log4j.helpers.LogLog;
import org.ikernits.lib.common.Log4jUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JettyMain {
    public static void main(String[] args) throws Exception {
        Log4jUtils.configureIfRequired();

        String serverAction = System.getProperty("server.action");
        if (serverAction == null) {
            LogLog.error("system propery server.action must be set to one of: start, shutdown");
        } else {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/spring-context-jetty.xml");
            JettyServerLifecycleService jettyServerService = context.getBean("jettyServerLifecycleService", JettyServerLifecycleService.class);
            switch (serverAction) {
                case "start":
                    jettyServerService.startServer();
                    break;
                case "shutdown":
                    jettyServerService.sendShutdown();
                    break;
                default:
                    throw new IllegalStateException("unsupported command: '" + serverAction + "'");
            }
        }
    }
}
