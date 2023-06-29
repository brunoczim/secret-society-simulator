package io.github.brunoczim.secsocsim.tcp;

class TcpCommandTable {
    private TcpCommandParser[] commandParsers;

    static final int MESSAGE_TAG = 221;

    TcpCommandTable() {
        TcpCommandParser nopCommandParser = new TcpNopCommandParser();
        this.commandParsers = new TcpCommandParser[256];
        for (int i = 0; i < this.commandParsers.length; i++) {
            this.commandParsers[i] = nopCommandParser;
        }
        this.commandParsers[TcpCommandTable.MESSAGE_TAG] = new TcpMessageCommandParser();
    }

    TcpCommand parseNext(TcpReader reader) {
        return null;
    }
}
