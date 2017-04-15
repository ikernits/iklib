package org.ikernits.lib.common.io.daemon;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.ikernits.lib.common.Log4jUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestIoDaemon {
    static {
        Log4jUtils.configureWithDefaults();
    }
    private static final Logger log = Logger.getLogger(TestIoDaemon.class);

    private IoDaemon ioDaemon;

    @AfterMethod
    public void afterTest() throws InterruptedException {
        ioDaemon.terminate(1000, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        Assert.assertFalse(ioDaemon.isActive());
        ioDaemon = null;
    }

    @Test
    public void testSmallLoopbackIo() throws IOException, InterruptedException {
        ioDaemon = new IoDaemon("small-io" ,IoStreamSourceLoopbackImpl.class);
        ioDaemon.start();
        Thread.sleep(100);
        Assert.assertTrue(ioDaemon.isActive());

        byte[] outData = RandomStringUtils.randomAlphanumeric(100).getBytes();
        ioDaemon.getOutputStream().write(outData);
        ioDaemon.getOutputStream().flush();

        Thread.sleep(1000);

        Assert.assertEquals(ioDaemon.getInputStream().available(), 100);

        byte[] inData = new byte[100];
        Assert.assertEquals(ioDaemon.getInputStream().read(inData), 100);
        Assert.assertEquals(inData, outData);
    }

    @Test
    public void testStartFailed() throws InterruptedException {
        ioDaemon = new IoDaemon("start-failed", IoStreamSourceStartFailedImpl.class);
        ioDaemon.start();
        Thread.sleep(1000);
        Assert.assertFalse(ioDaemon.isActive());
        Assert.assertEquals(ioDaemon.getExitValue(), 1);
    }

    @Test
    public void testReadFailed() throws InterruptedException {
        ioDaemon = new IoDaemon("read-failed", IoStreamSourceReadFailedImpl.class);
        ioDaemon.start();
        Thread.sleep(1000);
        Assert.assertFalse(ioDaemon.isActive());
        Assert.assertEquals(ioDaemon.getExitValue(), 2);
    }

    @Test
    public void testWriteFailed() throws InterruptedException, IOException {
        ioDaemon = new IoDaemon("write-failed", IoStreamSourceWriteFailedImpl.class);
        ioDaemon.start();
        ioDaemon.getOutputStream().write(new byte[100]);
        ioDaemon.getOutputStream().flush();
        Thread.sleep(1000);
        Assert.assertFalse(ioDaemon.isActive());
        Assert.assertEquals(ioDaemon.getExitValue(), 3);
    }

    @Test
    public void testHaltedIo() throws InterruptedException {
        ioDaemon = new IoDaemon("halted-io", IoStreamSourceHaltImpl.class);
        ioDaemon.start();
        Thread.sleep(1000);
        Assert.assertTrue(ioDaemon.isActive());
        log.info("shutting ioDaemon down");
        boolean result = ioDaemon.terminate(1000, TimeUnit.MILLISECONDS);
        log.info("ioDaemon terminated");

        Assert.assertTrue(result);
        Assert.assertEquals(ioDaemon.getExitValue(), 137);
    }

    @Test
    public void testClosedIo() throws InterruptedException, IOException {
        ioDaemon = new IoDaemon("closed-io", IoStreamSourceLoopbackImpl.class);
        ioDaemon.start();
        Thread.sleep(1000);
        Assert.assertTrue(ioDaemon.isActive());
        ioDaemon.getOutputStream().close();
        ioDaemon.getInputStream().close();
        Thread.sleep(1000);
        Assert.assertFalse(ioDaemon.isActive());
        Assert.assertEquals(ioDaemon.getExitValue(), 0);
    }
}
