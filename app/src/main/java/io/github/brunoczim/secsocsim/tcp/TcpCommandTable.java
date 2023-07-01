package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;

class TcpCommandTable {
    private TcpCommandParser[] commandParsers;

    static final int MESSAGE_TAG = 0xD3;

    TcpCommandTable() {
        TcpCommandParser nopCommandParser = new TcpNopCommandParser();
        this.commandParsers = new TcpCommandParser[0x100];
        for (int i = 0; i < this.commandParsers.length; i++) {
            this.commandParsers[i] = nopCommandParser;
        }
        this.commandParsers[TcpCommandTable.MESSAGE_TAG] = new TcpMessageCommandParser();
    }

    TcpCommand parseNext(TcpReader reader) throws IOException {
        int index = ((int) reader.readByte()) & 0xff;
        return this.commandParsers[index].parse(reader);
    }
}
