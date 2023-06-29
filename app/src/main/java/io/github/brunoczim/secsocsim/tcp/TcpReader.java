package io.github.brunoczim.secsocsim.tcp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.nio.charset.Charset;

class TcpReader {
    private InputStream stream;

    TcpReader(InputStream stream) {
        this.stream = stream;
    }

    int readOptionalByte() throws IOException {
        return this.stream.read();
    }

    byte readByte() throws IOException {
        int byteInteger = this.readOptionalByte();
        if (byteInteger < 0) {
            throw new EOFException();
        }
        return (byte) byteInteger;
    }

    byte[] readExactly(int count) throws IOException {
        byte[] buffer = new byte[count];
        this.readExactly(buffer, 0, count);
        return buffer;
    }

    void readExactly(byte[] buffer, int offset, int count) throws IOException {
        int read = 0;
        while (read < count) {
            this.stream.read(buffer, read + offset, count - read);
        }
    }

    int readInt() throws IOException {
        int intSize = 4;
        byte[] buffer = this.readExactly(intSize);
        int integer = 0;
        while (intSize > 0) {
            intSize--;
            integer <<= 8;
            integer |= buffer[intSize];
        }
        return integer;
    }

    int readNonNegativeInt() throws IOException {
        int integer = this.readInt();
        if (integer < 0) {
            throw new InvalidObjectException("expected a non-negative integer in a TCP stream");
        }
        return integer;
    }

    byte[] readByteArray() throws IOException {
        int size = this.readInt();
        return this.readExactly(size);
    }

    String readString() throws IOException {
        byte[] bytes = this.readByteArray();
        return new String(bytes, Charset.forName("UTF-8"));
    }
}
