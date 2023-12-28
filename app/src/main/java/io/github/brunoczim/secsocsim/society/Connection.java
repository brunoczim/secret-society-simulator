package io.github.brunoczim.secsocsim.society;

import java.io.Closeable;

public interface Connection extends Closeable {
    public void write(byte[] bytes) throws Exception;

    public void read(byte[] bytes) throws Exception;
}
