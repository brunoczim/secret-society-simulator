package io.github.brunoczim.secsocsim.ui;

@FunctionalInterface
public interface ConnectionOpeningHandler {
    public void openConnection(String address, SocietyUi societyUi)
        throws Exception;
}
