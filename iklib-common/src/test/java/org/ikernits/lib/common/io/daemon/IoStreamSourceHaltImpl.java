package org.ikernits.lib.common.io.daemon;

import org.apache.log4j.helpers.LogLog;
import org.ikernits.lib.common.io.IoStreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class IoStreamSourceHaltImpl implements IoStreamSource {
    PipedInputStream pipedInputStream = new PipedInputStream();
    PipedOutputStream pipedOutputStream = new PipedOutputStream();

    @Override
    public InputStream getInputStream() {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return pipedOutputStream;
    }

    public IoStreamSourceHaltImpl() {
        try {
            pipedInputStream.connect(pipedOutputStream);
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    //noinspection InfiniteLoopStatement,StatementWithEmptyBody
                    while (true);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
