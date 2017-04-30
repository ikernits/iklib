package org.ikernits.lib.jetty;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Required;

public class JettyServerLifecycleService implements DisposableBean {
    private static final Logger log = Logger.getLogger(JettyServerLifecycleService.class);

    protected Server server;

    @Required
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void destroy() throws Exception {
        stopServer();
    }

    public void startServer() {
        try {
            log.info("server start requested");
            server.start();
            log.info("server is started");
        } catch (Exception e) {
            throw new RuntimeException("failed to start server", e);
        }
    }

    public void stopServer() {
        if (server != null && server.isRunning()) {
            try {
                log.info("server stop requested");
                server.stop();
                log.info("server is stopped");
            } catch (Exception e) {
                throw new RuntimeException("failed to stop server", e);
            }
        }
    }
}
