package io.github.brunoczim.secsocsim.ui;

@FunctionalInterface
public interface ConnectionRequestHandler {
    public void requestConnection(String address, RoomUi roomUi) throws Exception;
}
