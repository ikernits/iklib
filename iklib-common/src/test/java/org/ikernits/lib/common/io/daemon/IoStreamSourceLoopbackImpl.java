package org.ikernits.lib.common.io.daemon;

import org.ikernits.lib.common.io.IoStreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class IoStreamSourceLoopbackImpl implements IoStreamSource {
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

    public IoStreamSourceLoopbackImpl() {
        try {
            pipedInputStream.connect(pipedOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
