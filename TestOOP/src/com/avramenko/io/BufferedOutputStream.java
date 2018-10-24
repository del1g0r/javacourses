package com.avramenko.io;


import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {

    static private final int DEFAULT_BUFFER_SIZE = 1024;

    private OutputStream outputStream;
    private byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    private int index;

    public BufferedOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        if (index == buffer.length) {
            flush();
        }
        buffer[index] = (byte) b;
        index++;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] array, int offset, int length) throws IOException {
        if (length <= buffer.length - index) {
            System.arraycopy(array, offset, buffer, index, length);
            index += length;
        } else {
            flush();
            outputStream.write(array, offset, length);
        }
    }

    @Override
    public void flush() throws IOException {
        outputStream.write(buffer, 0, index);
        index = 0;
    }

    @Override
    public void close() throws IOException {
        flush();
        outputStream.close();
    }
}