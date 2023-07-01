package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;
import java.net.Socket;

class TcpMessageCommand implements TcpCommand {
    private String message;

    TcpMessageCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(TcpPeer peer, Socket sender) throws IOException {
        peer.sendMessage(this.message);
    }

    @Override
    public void emit(TcpWriter writer) throws IOException {
        writer.writeString(this.message);
    }
}
