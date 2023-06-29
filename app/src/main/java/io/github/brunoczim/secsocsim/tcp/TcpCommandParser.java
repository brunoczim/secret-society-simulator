package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

@FunctionalInterface
interface TcpCommandParser {
    TcpCommand parse(TcpReader reader) throws IOException;
}
