package com.avramenko.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {

    static private final int DEFAULT_BUFFER_SIZE = 1024;

    private InputStream inputStream;
    private byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    private int index;
    private int count;

    public BufferedInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        if (isEOF()) {
            return -1;
        }
        int value = buffer[index];
        index++;

        return value;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] array, int offset, int length) throws IOException {
        if (isEOF()) {
            return -1;
        }
        int elementsInBuffer = count - index;
        int elementsMoved = length < elementsInBuffer ? length : elementsInBuffer;
        System.arraycopy(buffer, index, array, offset, elementsMoved);
        index += elementsMoved;
        return elementsMoved;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    private boolean isEOF() throws IOException {
        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }
        return count == -1;
    }
}