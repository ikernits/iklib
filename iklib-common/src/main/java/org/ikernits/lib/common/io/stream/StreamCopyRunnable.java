package org.ikernits.lib.common.io.stream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.ikernits.lib.spring.process.ProcessExecutionService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamCopyRunnable implements Runnable {
    private static final Logger log = Logger.getLogger(StreamCopyRunnable.class);

    private final String name;
    private final InputStream in;
    private final OutputStream out;
    private final int limit;
    private final byte[] buffer;

    private final ByteArrayOutputStream data = new ByteArrayOutputStream();
    private volatile int totalBytesRead = 0;
    private volatile int totalBytesWritten = 0;
    private volatile Throwable error = null;

    public StreamCopyRunnable(String name, InputStream in, OutputStream out, int limit, int bufferSize) {
        this.name = name;
        this.in = in;
        this.limit = limit;
        this.out = out != null ? out : data;
        this.buffer = new byte[bufferSize];
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        Thread.currentThread().setName(name);
        try {
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                totalBytesRead += bytesRead;
                if (limit < 0 || totalBytesWritten < limit) {
                    int bytesToCopy;
                    if (limit < 0) {
                        bytesToCopy = bytesRead;
                    } else {
                        bytesToCopy = Math.min(bytesRead, limit - totalBytesWritten);
                    }
                    out.write(buffer, 0, bytesToCopy);
                    out.flush();
                    totalBytesWritten += bytesToCopy;
                }
            }
        } catch (Throwable th) {
            error = th;
            log.warn("stream IO operation failed", th);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            Thread.currentThread().setName(threadName);
        }
    }

    public ProcessExecutionService.IoResult getIoResult() {
        return new ProcessExecutionService.IoResult(data.toByteArray(), totalBytesRead, totalBytesWritten, error);
    }
}
