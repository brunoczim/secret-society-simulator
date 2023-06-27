package io.github.brunoczim.secsocsim.p2p;

@FunctionalInterface
public interface IncomingMessageHandler {
    public void receive(String message);
}
