package com.avramenko.datastructures.list;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LinkedListTest {
    List arrayWithData;

    @Before
    public void before() {
        arrayWithData = new LinkedList();
//        arrayWithData = new ArrayList();
        arrayWithData.add("str1");
        arrayWithData.add("str2");
        arrayWithData.add("str3");
    }

    @Test
    public void testAddIntoEmptyList() {
        arrayWithData.clear();
        assertEquals(0, arrayWithData.size());

        arrayWithData.add("one");
        assertEquals("one", arrayWithData.get(0));
        assertEquals(1, arrayWithData.size());
    }

    @Test
    public void testAddIntoNotEmptyList() {
        arrayWithData.add("str4");
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("str2", arrayWithData.get(1));
        assertEquals("str3", arrayWithData.get(2));
        assertEquals("str4", arrayWithData.get(3));
        assertEquals(4, arrayWithData.size());
    }

    @Test
    public void testAddEnsureCapacity() {
        arrayWithData.add("str4");
        arrayWithData.add("str5");
        arrayWithData.add("str6");
        assertEquals("str6", arrayWithData.get(5));
        assertEquals(6, arrayWithData.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexInvalidIndex() {
        arrayWithData.add("str5", 4);
    }

    @Test
    public void testAddIndexEmptyList() {
        arrayWithData.clear();
        assertEquals(0, arrayWithData.size());
        arrayWithData.add("begin", 0);
        assertEquals("begin", arrayWithData.get(0));
        assertEquals(1, arrayWithData.size());
    }

    @Test
    public void testAddIndexBegin() {
        arrayWithData.add("begin", 0);
        assertEquals("begin", arrayWithData.get(0));
        assertEquals("str1", arrayWithData.get(1));
        assertEquals("str2", arrayWithData.get(2));
        assertEquals("str3", arrayWithData.get(3));
        assertEquals(4, arrayWithData.size());
    }

    @Test
    public void testAddIndexCenter() {
        arrayWithData.add("center", 1);
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("center", arrayWithData.get(1));
        assertEquals("str2", arrayWithData.get(2));
        assertEquals("str3", arrayWithData.get(3));
        assertEquals(4, arrayWithData.size());
    }

    @Test
    public void testAddIndexEnd() {
        arrayWithData.add("end", 3);
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("str2", arrayWithData.get(1));
        assertEquals("str3", arrayWithData.get(2));
        assertEquals("end", arrayWithData.get(3));
        assertEquals(4, arrayWithData.size());
    }

    @Test
    public void testIndexOfNull() {
        arrayWithData.add(null);
        arrayWithData.add("str5");
        int actual = arrayWithData.indexOf(null);
        assertEquals(3, actual);
    }

    @Test
    public void testIndexOf() {
        int actual = arrayWithData.indexOf("str2");
        assertEquals(1, actual);
    }

    @Test
    public void testIndexOfDoesNotExist() {
        int actual = arrayWithData.indexOf("str50");
        assertEquals(-1, actual);
    }

    @Test
    public void testLastIndexOfNull() {
        arrayWithData.add(null);
        arrayWithData.add(null);
        int actual = arrayWithData.lastIndexOf(null);
        assertEquals(4, actual);
    }

    @Test
    public void testLastIndexOf() {
        arrayWithData.add("str2", 2);
        int actual = arrayWithData.lastIndexOf("str2");
        assertEquals(2, actual);
    }

    @Test
    public void testLastIndexOfDoesNotExist() {
        int actual = arrayWithData.lastIndexOf("str50");
        assertEquals(-1, actual);
    }

    @Test
    public void testContainsTrue() {
        assertTrue(arrayWithData.contains("str3"));
    }

    @Test
    public void testContainsFalse() {
        assertFalse(arrayWithData.contains("str50"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetInvalidIndex() {
        arrayWithData.set("str4", 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetEmptyList() {
        arrayWithData.clear();
        assertEquals(0, arrayWithData.size());
        arrayWithData.set("setValue", 0);
        assertEquals("setValue", arrayWithData.get(0));
        assertEquals(1, arrayWithData.size());
    }

    @Test
    public void testSetBegin() {
        arrayWithData.set("setValue", 0);
        assertEquals("setValue", arrayWithData.get(0));
        assertEquals("str2", arrayWithData.get(1));
        assertEquals("str3", arrayWithData.get(2));
        assertEquals(3, arrayWithData.size());
    }

    @Test
    public void testSetCenter() {
        arrayWithData.set("setValue", 1);
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("setValue", arrayWithData.get(1));
        assertEquals("str3", arrayWithData.get(2));
        assertEquals(3, arrayWithData.size());
    }

    @Test
    public void testSetEnd() {
        arrayWithData.set("setValue", 2);
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("str2", arrayWithData.get(1));
        assertEquals("setValue", arrayWithData.get(2));
        assertEquals(3, arrayWithData.size());
    }

    @Test
    public void testClear() {
        arrayWithData.clear();
        assertEquals(0, arrayWithData.size());
    }

    @Test
    public void testGetBegin() {
        Object actual = arrayWithData.get(0);
        assertEquals("str1", actual);
    }

    @Test
    public void testGetCenter() {
        Object actual = arrayWithData.get(1);
        assertEquals("str2", actual);
    }

    @Test
    public void testGetEnd() {
        Object actual = arrayWithData.get(2);
        assertEquals("str3", actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetInvalidIndex() {
        arrayWithData.get(50);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFromEmptyList() {
        arrayWithData.clear();
        assertEquals(0, arrayWithData.size());
        arrayWithData.remove(0);
    }

    @Test
    public void testRemoveBegin() {
        arrayWithData.remove(0);
        assertEquals("str2", arrayWithData.get(0));
        assertEquals("str3", arrayWithData.get(1));
        assertEquals(2, arrayWithData.size());
    }

    @Test
    public void testRemoveCenter() {
        arrayWithData.remove(1);
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("str3", arrayWithData.get(1));
        assertEquals(2, arrayWithData.size());
    }

    @Test
    public void testRemoveEnd() {
        arrayWithData.remove(2);
        assertEquals("str1", arrayWithData.get(0));
        assertEquals("str2", arrayWithData.get(1));
        assertEquals(2, arrayWithData.size());
    }
}