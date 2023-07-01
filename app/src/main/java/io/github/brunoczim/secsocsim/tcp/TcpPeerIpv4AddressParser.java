package io.github.brunoczim.secsocsim.tcp;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

class TcpPeerIpv4AddressParser {
    public TcpPeerAddress parse(String coded) {
        try {
            int splitPos = coded.lastIndexOf(':');
            String inetAddressPiece = coded.substring(0, splitPos);
            String portPiece = coded.substring(splitPos);
            Inet4Address inetAddress = (Inet4Address) InetAddress.getByName(inetAddressPiece);
            int port = Integer.parseInt(portPiece);
            return new TcpPeerIpv4Address(inetAddress, port);
        } catch (UnknownHostException exc) {
            throw new IllegalArgumentException(exc);
        }
    }
}
