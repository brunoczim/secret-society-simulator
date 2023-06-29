package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

class TcpNopCommandParser implements TcpCommandParser {
    TcpNopCommandParser() {
    }

    @Override
    public TcpCommand parse(TcpReader reader) throws IOException {
        return new TcpNopCommand();
    }
}
