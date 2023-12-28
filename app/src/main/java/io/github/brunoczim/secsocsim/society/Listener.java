package io.github.brunoczim.secsocsim.society;

import java.io.Closeable;

public interface Listener extends Closeable {
    public Connection accept() throws Exception;
}
