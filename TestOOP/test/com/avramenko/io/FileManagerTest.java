package com.avramenko.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {
    @Before
    public void before() {
        new File("c:\\test0").mkdir();
        new File("c:\\test0\\0").mkdir();
        new File("c:\\test0\\1").mkdir();
        try {
            new File("c:\\test0\\1\\file").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new File("c:\\test0\\2").mkdir();
    }

    @Test
    public void testCountFiles() {
        assertEquals(1, FileManager.countFiles("c:\\test0"));
    }

    @Test
    public void testCountDirs() {
        assertEquals(3, FileManager.countDirs("c:\\test0"));
    }

    @Test
    public void testCopy() {

        try {
            FileManager.copy("c:\\test0", "c:\\test1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(3, FileManager.countDirs("c:\\test0"));
        assertEquals(1, FileManager.countFiles("c:\\test0"));
        assertEquals(3, FileManager.countDirs("c:\\test1"));
        assertEquals(1, FileManager.countFiles("c:\\test1"));
    }

    @Test
    public void testMove() {

        try {
            FileManager.move("c:\\test0", "c:\\test1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(0, FileManager.countDirs("c:\\test0"));
        assertEquals(0, FileManager.countFiles("c:\\test0"));
        assertEquals(3, FileManager.countDirs("c:\\test1"));
        assertEquals(1, FileManager.countFiles("c:\\test1"));
    }


    private void delete(String path) {
        File root = new File(path);
        if (root.exists()) {
            new File(path + "\\2").delete();
            new File(path + "\\1\\file").delete();
            new File(path + "\\1").delete();
            new File(path + "\\0").delete();
            root.delete();
        }
    }

    @After
    public void after() {
        delete("c:\\test0");
        delete("c:\\test1");
    }
}




