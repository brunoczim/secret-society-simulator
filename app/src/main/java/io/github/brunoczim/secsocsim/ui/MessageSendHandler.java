package io.github.brunoczim.secsocsim.ui;

@FunctionalInterface
public interface MessageSendHandler {
    public void sendMessage(String message) throws Exception;
}
