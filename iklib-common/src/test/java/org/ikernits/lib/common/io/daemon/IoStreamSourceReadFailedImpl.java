package org.ikernits.lib.common.io.daemon;

import org.ikernits.lib.common.io.IoStreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class IoStreamSourceReadFailedImpl implements IoStreamSource {
    PipedInputStream pipedInputStream = new PipedInputStream();
    PipedOutputStream pipedOutputStream = new PipedOutputStream();

    @Override
    public InputStream getInputStream() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Read failed");
            }
        };
    }

    @Override
    public OutputStream getOutputStream() {
        return pipedOutputStream;
    }

    public IoStreamSourceReadFailedImpl() {
        try {
            pipedInputStream.connect(pipedOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
