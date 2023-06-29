package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

interface TcpCommand {
    void execute(TcpPeer peer) throws IOException;

    void emit(TcpWriter writer) throws IOException;
}
