package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;
import java.net.Socket;

interface TcpCommand {
    void execute(TcpPeer peer, Socket sender) throws IOException;

    void emit(TcpWriter writer) throws IOException;
}
