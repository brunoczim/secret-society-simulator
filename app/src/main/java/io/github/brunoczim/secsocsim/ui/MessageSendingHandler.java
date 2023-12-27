package io.github.brunoczim.secsocsim.ui;

@FunctionalInterface
public interface MessageSendingHandler {
    public void sendMessage(String message) throws Exception;
}
