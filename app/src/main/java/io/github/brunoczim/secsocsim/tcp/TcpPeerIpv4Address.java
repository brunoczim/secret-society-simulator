package io.github.brunoczim.secsocsim.tcp;

import java.net.Inet4Address;
import java.net.InetAddress;

class TcpPeerIpv4Address extends TcpPeerAddress {
    private Inet4Address inetAddress;

    TcpPeerIpv4Address(Inet4Address inetAddress, int port) {
        super(port);
        this.inetAddress = inetAddress;
    }

    void setInetAddress(Inet4Address inetAddress) {
        this.inetAddress = inetAddress;
    }

    InetAddress getInetAddress() {
        return this.inetAddress;
    }

    public String toString() {
        return this.getInetAddress() + ":" + this.getPort();
    }
}
