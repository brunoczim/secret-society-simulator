package io.github.brunoczim.secsocsim.p2p;

@FunctionalInterface
public interface Firewall {
    public boolean shouldConnect(String address);
}
