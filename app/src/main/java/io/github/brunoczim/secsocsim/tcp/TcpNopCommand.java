package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

class TcpNopCommand implements TcpCommand {
    TcpNopCommand() {
    }

    @Override
    public void execute(TcpPeer peer) throws IOException {
    }

    @Override
    public void emit(TcpWriter writer) throws IOException {
    }
}
