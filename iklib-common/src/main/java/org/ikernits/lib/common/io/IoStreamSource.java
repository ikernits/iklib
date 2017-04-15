package org.ikernits.lib.common.io;

import java.io.InputStream;
import java.io.OutputStream;

public interface IoStreamSource {
    InputStream getInputStream();
    OutputStream getOutputStream();
}
