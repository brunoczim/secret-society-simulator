package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;
import java.net.InetAddress;

import io.github.brunoczim.secsocsim.p2p.Peer;
import io.github.brunoczim.secsocsim.p2p.PeerFactory;

public class TcpPeerFactory implements PeerFactory {
    public static int DEFAULT_PORT = 3303;

    @Override
    public Peer createPeer(String parentAddress, String localAddress) throws IOException {
        String[] pieces = parentAddress.split(":");
        if (pieces.length > 2) {
            throw new IOException("too many colons");
        }
        InetAddress parsedParenAddress = InetAddress.getByName(parentAddress);
        int parentPort = pieces.length == 2 ? Integer.parseInt(pieces[1])
                : TcpPeerFactory.DEFAULT_PORT;
        int localPort = localAddress.length() > 0 ? Integer.parseInt(localAddress)
                : TcpPeerFactory.DEFAULT_PORT;
        return new TcpPeer(parsedParenAddress, parentPort, localPort);
    }
}
