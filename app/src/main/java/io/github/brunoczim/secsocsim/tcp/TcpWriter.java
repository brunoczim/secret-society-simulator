package io.github.brunoczim.secsocsim.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

class TcpWriter {
    private OutputStream stream;

    TcpWriter(OutputStream stream) {
        this.stream = stream;
    }

    void writeByte(byte byteInteger) throws IOException {
        this.stream.write(byteInteger);
    }

    void writeExactly(byte[] buffer) throws IOException {
        this.writeExactly(buffer, 0, buffer.length);
    }

    void writeExactly(byte[] buffer, int offset, int count) throws IOException {
        this.stream.write(buffer, offset, count);
    }

    void writeInt(int integer) throws IOException {
        int intSize = 4;
        byte[] buffer = new byte[intSize];
        while (intSize > 0) {
            buffer[buffer.length - intSize] = (byte) integer;
            integer >>>= 8;
            intSize--;
        }
        this.writeExactly(buffer);
    }

    void writeByteArray(byte[] byteArray) throws IOException {
        this.writeInt(byteArray.length);
        this.writeExactly(byteArray);
    }

    void writeString(String string) throws IOException {
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        this.writeByteArray(bytes);
    }
}
