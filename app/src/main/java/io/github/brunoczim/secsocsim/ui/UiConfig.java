package io.github.brunoczim.secsocsim.ui;

public abstract class UiConfig {
    private ConnectionOpeningHandler connectionOpeningHandler;
    private MessageSendingHandler messageSendingHandler;

    public UiConfig setConnectionOpeningHandler(
        ConnectionOpeningHandler handler
    ) {
        this.connectionOpeningHandler = handler;
        return this;
    }

    public UiConfig setMessageSendingHandler(MessageSendingHandler handler) {
        this.messageSendingHandler = handler;
        return this;
    }

    public ConnectionOpeningHandler getConnectionOpeningHandler() {
        return this.connectionOpeningHandler;
    }

    public MessageSendingHandler getMessageSendingHandler() {
        return this.messageSendingHandler;
    }

    public abstract MainUi finish();
}
