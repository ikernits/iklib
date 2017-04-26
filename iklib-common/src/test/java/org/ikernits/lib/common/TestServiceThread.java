package org.ikernits.lib.common;

import org.ikernits.lib.common.ConcurrentUtils.ServiceThread;
import org.ikernits.lib.common.ConcurrentUtils.ServiceThreadTask;
import org.mockito.InOrder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class TestServiceThread {

    class TestException extends RuntimeException {
    }

    public void runThread(ServiceThread thread, Class<?> exception) throws InterruptedException {
        runThread(thread, 100, exception);
    }

    public void runThread(ServiceThread thread, long runTime) throws InterruptedException {
        runThread(thread, runTime, null);
    }


    public void runThread(ServiceThread thread, long runTime, Class<?> exception) throws InterruptedException {
        try {
            thread.start();
            Thread.sleep(runTime);
            thread.shutdown();
            thread.awaitTermination(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            Assert.assertEquals(e.getCause().getClass(), exception);
        } catch (TimeoutException e) {
            Assert.assertEquals(e.getClass(), exception);
        }
    }

    public boolean sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
        return true;
    }

    @Test
    public void testNormalCompletion() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        thread.shutdown();
        runThread(thread, null);

        order.verify(task).beforeRun();
        order.verify(task, never()).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testNormalPerform() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, 100);

        order.verify(task).beforeRun();
        order.verify(task, atLeastOnce()).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testInterruptFromPerform() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doThrow(new InterruptedException()).when(task).perform();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, 100);

        order.verify(task).beforeRun();
        order.verify(task, atLeast(2)).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testInterruptToPerform() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doAnswer(i -> sleep(2000)).when(task).perform();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, TestException.class);

        order.verify(task).beforeRun();
        order.verify(task, times(1)).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testBeforeRunError() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doThrow(new TestException()).when(task).beforeRun();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, TestException.class);

        order.verify(task).beforeRun();
        order.verify(task, never()).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testPerformError() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doThrow(new TestException()).when(task).perform();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, TestException.class);

        order.verify(task).beforeRun();
        order.verify(task, times(1)).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testAfterRunError() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doThrow(new TestException()).when(task).afterRun();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, TestException.class);

        order.verify(task).beforeRun();
        order.verify(task, atLeastOnce()).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testPerformAndAfterRunError() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doThrow(new TestException()).when(task).perform();
        doThrow(new RuntimeException()).when(task).afterRun();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, TestException.class);

        order.verify(task).beforeRun();
        order.verify(task).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testUpdateTimer() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doAnswer(inv -> sleep(100)).when(task).perform();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        thread.start();
        for (int i = 0; i < 10; ++i) {
            Thread.sleep(50);
            if (thread.getMillisSinceLastUpdate() > 200) {
                Assert.fail("keep alive millis are expected to be ~100");
            }
        }
        thread.shutdown();
        thread.awaitTermination(1, TimeUnit.SECONDS);

        order.verify(task).beforeRun();
        order.verify(task, atLeastOnce()).perform();
        order.verify(task).afterRun();
    }

    @Test
    public void testThreadName() throws Exception {
        ServiceThreadTask task = mock(ServiceThreadTask.class);
        doAnswer(inv -> {
            Assert.assertEquals(Thread.currentThread().getName(), "test");
            return null;
        }).when(task).perform();
        InOrder order = inOrder(task);

        ServiceThread thread = ConcurrentUtils.createServiceThread("test", task);
        runThread(thread, 100);

        order.verify(task).beforeRun();
        order.verify(task, atLeastOnce()).perform();
        order.verify(task).afterRun();
    }
}
