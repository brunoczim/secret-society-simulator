package io.github.brunoczim.secsocsim.p2p;

import java.io.Closeable;

public interface Peer extends Closeable {
    public void send(String message);

    public void setIncomingMessageHandler(IncomingMessageHandler handler);
}
