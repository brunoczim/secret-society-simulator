package io.github.brunoczim.secsocsim.society;

public interface Address {
    public Connection connect() throws Exception;

    public Listener listen() throws Exception;
}
