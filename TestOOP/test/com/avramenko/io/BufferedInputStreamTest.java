package com.avramenko.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class BufferedInputStreamTest {

    private byte[] array = new byte[1_000];
    private BufferedInputStream bufferedInputStream;

    @Before
    public void before() {
        new Random().nextBytes(array);
        bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(array));
    }

    @Test
    public void testRead() throws IOException {
        for (byte b : array) {
            byte value = (byte) bufferedInputStream.read();
            assertEquals(b, value);
        }
    }

    @Test
    public void testReadToArray() throws IOException {
        byte[] buffer = new byte[512];
        int count = bufferedInputStream.read(buffer);
        assertEquals(512, count);
        for (int i = 0; i < buffer.length; i++) {
            assertEquals(array[i], buffer[i]);
        }

        count = bufferedInputStream.read(buffer);
        assertEquals(488, count);
        for (int i = 512; i < 1000; i++) {
            assertEquals(array[i], buffer[i - 512]);
        }

        count = bufferedInputStream.read(buffer);
        assertEquals(-1, count);
    }

    @Test
    public void testReadWihOffset() throws IOException {
        byte[] data = new byte[512];
        int off = 128;
        int len = 100;
        bufferedInputStream.read(data, off, len);
        for (int i = 0; i < len; i++) {
            assertEquals(array[i], data[i + off]);
        }
    }

    @After
    public void after() throws IOException {
        bufferedInputStream.close();
    }
}