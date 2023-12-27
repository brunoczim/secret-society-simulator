package io.github.brunoczim.secsocsim.ui;

public interface SocietyUi {
    public void logIncomingMessage(String text);

    public void logSystemMessage(String text);

    public void close();
}
