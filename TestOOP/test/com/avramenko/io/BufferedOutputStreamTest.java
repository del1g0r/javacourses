package com.avramenko.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class BufferedOutputStreamTest {

    byte[] array = new byte[10_000];
    ByteArrayOutputStream outputStream;
    BufferedOutputStream bufferedOutputStream;

    @Before
    public void before() {
        new Random().nextBytes(array);
        outputStream = new ByteArrayOutputStream();
        bufferedOutputStream = new BufferedOutputStream(outputStream);
    }

    @Test
    public void testWrite() throws IOException {
        for (byte b : array) {
            bufferedOutputStream.write(b);
        }
        bufferedOutputStream.flush();

        byte[] data = outputStream.toByteArray();
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], data[i]);
        }
    }

    @Test
    public void testWriteWithAutoFlush() throws IOException {
        // array -> 10_000
        // buffer_size -> 1024
        // auto_flush -> 9 times
        MockByteArrayOutputStream outputStream = new MockByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        for (byte b : array) {
            bufferedOutputStream.write(b);
        }
        // 10 flush
        bufferedOutputStream.close();

        byte[] data = outputStream.toByteArray();
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], data[i]);
        }

        //assertEquals(10, outputStream.flushCount);
    }

    @Test
    public void testWriteFromArray() throws IOException {
        byte[] data;
        byte[] dataToWrite = new byte[2_000];
        System.arraycopy(array, 0, dataToWrite, 0, 2_000);
        bufferedOutputStream.write(dataToWrite);
        bufferedOutputStream.flush();
        data = outputStream.toByteArray();
        for (int i = 0; i < dataToWrite.length; i++) {
            assertEquals(dataToWrite[i], data[i]);
        }
    }

    @Test
    public void testReadWihOffset() throws IOException {
        byte[] data;
        byte[] dataToWrite = new byte[2_000];
        System.arraycopy(array, 0, dataToWrite, 0, 2_000);
        int off = 1000;
        int len = 512;
        bufferedOutputStream.write(dataToWrite, off, len);
        bufferedOutputStream.flush();
        data = outputStream.toByteArray();
        for (int i = 0; i < len; i++) {
            assertEquals(dataToWrite[off + i], data[i]);
        }
    }

    @After
    public void after() throws IOException {
        outputStream.close();
        bufferedOutputStream.close();
    }
}

class MockByteArrayOutputStream extends ByteArrayOutputStream {
    int flushCount;

    @Override
    public synchronized void write(byte[] b, int off, int len) {
        super.write(b, off, len);
        flushCount++;
    }

    public int getFlushCount() {
        return flushCount;
    }
}
