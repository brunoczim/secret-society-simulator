package io.github.brunoczim.secsocsim.tcp;

import java.net.InetAddress;

abstract class TcpPeerAddress {
    private int port;

    TcpPeerAddress(int port) {
        this.setPort(port);
    }

    void setPort(int port) {
        this.port = port;
    }

    int getPort() {
        return port;
    }

    abstract InetAddress getInetAddress();

    public abstract String toString();
}
