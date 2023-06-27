package io.github.brunoczim.secsocsim.ui;

public interface RoomUi {
    public void logIncomingMessage(String text);

    public void logSystemMessage(String text);

    public void close();
}
