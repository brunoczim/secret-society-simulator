package io.github.brunoczim.secsocsim.p2p;

import java.io.IOException;

@FunctionalInterface
public interface PeerFactory {
    public Peer createPeer(String parentAddress, String localAddress) throws IOException;
}
