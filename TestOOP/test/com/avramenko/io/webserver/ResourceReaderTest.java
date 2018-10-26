package com.avramenko.io.webserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class ResourceReaderTest {

    ResourceReader resourseReader;

    @Before
    public void before() {
        resourseReader = new ResourceReader("/root/");
    }

    @Test
    public void testGetContentName() throws IOException {
        String expected = "/index.html";
        String actual = resourseReader.getContentName("/index.html?q=1");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetContentFile() throws IOException {
        File expected = new File("/root/index.html");
        File actual = resourseReader.getContentFile("/index.html?q=1");
        assertEquals(expected, actual);
    }

}
