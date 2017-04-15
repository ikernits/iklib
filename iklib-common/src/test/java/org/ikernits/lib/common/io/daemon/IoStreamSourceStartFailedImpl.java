package org.ikernits.lib.common.io.daemon;

import org.ikernits.lib.common.io.IoStreamSource;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class IoStreamSourceStartFailedImpl implements IoStreamSource {
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

    public IoStreamSourceStartFailedImpl() {
        throw new IllegalStateException("Failed to start");
    }
}
