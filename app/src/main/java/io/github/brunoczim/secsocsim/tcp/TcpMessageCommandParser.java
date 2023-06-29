package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

class TcpMessageCommandParser implements TcpCommandParser {
    TcpMessageCommandParser() {
    }

    @Override
    public TcpCommand parse(TcpReader reader) throws IOException {
        String message = reader.readString();
        return new TcpMessageCommand(message);
    }
}
