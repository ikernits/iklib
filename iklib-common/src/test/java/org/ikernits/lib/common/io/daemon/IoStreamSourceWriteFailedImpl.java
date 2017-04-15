package org.ikernits.lib.common.io.daemon;

import org.ikernits.lib.common.io.IoStreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class IoStreamSourceWriteFailedImpl implements IoStreamSource {
    PipedInputStream pipedInputStream = new PipedInputStream();
    PipedOutputStream pipedOutputStream = new PipedOutputStream();

    @Override
    public InputStream getInputStream() {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Write failed");
            }
        };
    }

    public IoStreamSourceWriteFailedImpl() {
        try {
            pipedInputStream.connect(pipedOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
