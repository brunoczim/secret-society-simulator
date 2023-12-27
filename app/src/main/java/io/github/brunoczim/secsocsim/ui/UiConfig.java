package io.github.brunoczim.secsocsim.ui;

public abstract class UiConfig {
    private ConnectionRequestHandler connectionRequestHandler;
    private MessageSendHandler messageSendHandler;

    public UiConfig setConnectionRequestHandler(
        ConnectionRequestHandler handler
    ) {
        this.connectionRequestHandler = handler;
        return this;
    }

    public UiConfig setMessageSendHandler(MessageSendHandler handler) {
        this.messageSendHandler = handler;
        return this;
    }

    public ConnectionRequestHandler getConnectionRequestHandler() {
        return this.connectionRequestHandler;
    }

    public MessageSendHandler getMessageSendHandler() {
        return this.messageSendHandler;
    }

    public abstract MainUi finish();
}
