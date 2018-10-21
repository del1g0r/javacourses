package com.avramenko.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

public class FileAnalyzerTest {

    final String fileName = "c:\\test\\song";

    @Before
    public void before() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.println("Twinkle, twinkle, little star,");
        writer.println("How I wonder what you are.");
        writer.println("Up above the world so high,");
        writer.println("Like a diamond in the sky.");
        writer.println("Twinkle, twinkle, little star,");
        writer.println("How I wonder what you are!");
        writer.close();
    }

    @Test
    public void testScanFileCaseSensitive() throws IOException {
        assertEquals(2, FileAnalyzer.scanFile(fileName,  "twinkle", true));
    }

    @Test
    public void testScanFileCaseInsensitive() throws IOException {
        assertEquals(4, FileAnalyzer.scanFile(fileName,  "twinkle", false));
    }

    @After
    public void after() {
        new File(fileName).delete();
    }
}




