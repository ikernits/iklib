package org.ikernits.lib.jetty;

import org.apache.log4j.Logger;
import org.ikernits.lib.common.Log4jUtils;
import org.ikernits.lib.spring.http.HttpClientService.HttpClientException;
import org.ikernits.lib.spring.http.HttpClientService.HttpRequest;
import org.ikernits.lib.spring.http.HttpClientService.HttpResponse;
import org.ikernits.lib.spring.http.HttpClientServiceApacheImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

public class TestJettyServer {
    HttpClientServiceApacheImpl httpClientService;
    JettyMain jettyMain;

    static {
        Log4jUtils.configureWithDefaults();
    }

    private static final Logger log = Logger.getLogger(TestJettyServer.class);

    @BeforeMethod
    public void init() throws Exception {
        httpClientService = new HttpClientServiceApacheImpl();
        httpClientService.afterPropertiesSet();

        System.setProperty("server", "test");
        jettyMain = new JettyMain();
        jettyMain.startServer();
    }

    @AfterMethod
    public void destory() throws Exception {
        httpClientService.destroy();
        jettyMain.stopServer();
    }

    @Test
    public void testServer_200() throws Exception {
        HttpResponse response = httpClientService.execute(HttpRequest.get("http://localhost:8000/test_200").build());
        assertEquals(response.getCode(), (Integer) 200);
        assertEquals(response.getBodyAsString(), "OK");
    }

    @Test
    public void testServer_404() throws Exception {
        HttpClientException error = expectThrows(
            HttpClientException.class,
            () -> httpClientService.execute(HttpRequest.get("http://localhost:8000").build())
        );
        assertEquals(error.getType(), HttpClientException.Type.BadHttpCode);
        assertEquals(error.getResponse().getCode(), (Integer) 404);
    }
}
