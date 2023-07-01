package io.github.brunoczim.secsocsim.tcp;

import java.net.Inet6Address;
import java.net.InetAddress;

class TcpPeerIpv6Address extends TcpPeerAddress {
    private Inet6Address inetAddress;

    TcpPeerIpv6Address(Inet6Address inetAddress, int port) {
        super(port);
        this.inetAddress = inetAddress;
    }

    void setInetAddress(Inet6Address inetAddress) {
        this.inetAddress = inetAddress;
    }

    InetAddress getInetAddress() {
        return this.inetAddress;
    }

    public String toString() {
        return "[" + this.getInetAddress() + "]:" + this.getPort();
    }
}
