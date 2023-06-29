package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

class TcpMessageCommand implements TcpCommand {
    private String message;

    TcpMessageCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(TcpPeer peer) throws IOException {
    }

    @Override
    public void emit(TcpWriter writer) throws IOException {
        writer.writeString(this.message);
    }
}
