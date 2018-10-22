package com.avramenko.io;

import com.avramenko.datastructures.list.ArrayList;
import com.avramenko.datastructures.list.List;
import org.junit.Test;
import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class FileAnalyzerTest {

    final String line = "Twinkle, twinkle, little star,\r\nHow I wonder what you are.\r\nUp above the world so high,\r\nLike a diamond in the sky.\r\nTwinkle, twinkle, little star,\r\nHow I wonder what you are!\r\n";

    final ArrayList<String> sentences = new ArrayList<>(new String[]{
            "Twinkle, twinkle, little star,",
            "How I wonder what you are.",
            "Up above the world so high,",
            "Like a diamond in the sky.",
            "Twinkle, twinkle, little star,",
            "How I wonder what you are!"
    });

    final ArrayList<String> filteredSentences = new ArrayList<>(new String[]{
            "Twinkle, twinkle, little star,",
            "Twinkle, twinkle, little star,"
    });

    final String searchyString = "twinkle";

    @Test
    public void testReadSentences() throws IOException {
        List<String> actual = FileAnalyzer.readSentences(new ByteArrayInputStream(line.getBytes()));
        List<String> expected = sentences;
        assertThat(actual, is(expected));
    }

    @Test
    public void testFilter() {
        List<String> actual = FileAnalyzer.filter(sentences, searchyString, FileAnalyzer.DEFAULT_CASE_SENSITIVE);;
        List<String> expected = filteredSentences;
        assertThat(actual, is(expected));
    }

    @Test
    public void testCountOccuranceCaseSensitive() {
        int count = FileAnalyzer.countOccurance(sentences, searchyString, true);;
        assertEquals(2, count);
    }

    @Test
    public void testCountOccuranceCaseInsensitive() {
        int count = FileAnalyzer.countOccurance(sentences, searchyString, false);;
        assertEquals(4, count);
    }


}




