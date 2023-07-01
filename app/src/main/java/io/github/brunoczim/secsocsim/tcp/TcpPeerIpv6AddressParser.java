package io.github.brunoczim.secsocsim.tcp;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

class TcpPeerIpv6AddressParser {
    public TcpPeerAddress parse(String coded) {
        try {
            int splitPos = coded.lastIndexOf(':');
            String inetAddressPiece = coded.substring(0, splitPos);
            if (inetAddressPiece.length() < 2) {
                throw new IllegalArgumentException("ipv6 address is too short");
            }
            if (inetAddressPiece.charAt(0) != '[') {
                throw new IllegalAccessError("missing opening square bracket");
            }
            if ((inetAddressPiece.charAt(inetAddressPiece.length() - 1) != ']')) {
                throw new IllegalAccessError("missing closing square bracket");
            }
            inetAddressPiece = inetAddressPiece.substring(1, inetAddressPiece.length() - 1);
            String portPiece = coded.substring(splitPos);
            Inet6Address inetAddress = (Inet6Address) InetAddress.getByName(inetAddressPiece);
            int port = Integer.parseInt(portPiece);
            return new TcpPeerIpv6Address(inetAddress, port);
        } catch (UnknownHostException exc) {
            throw new IllegalArgumentException(exc);
        }
    }
}
