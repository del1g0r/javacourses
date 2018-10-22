package com.avramenko.io;

import com.avramenko.datastructures.list.ArrayList;
import com.avramenko.datastructures.list.List;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileAnalyzer {

    public static final boolean DEFAULT_CASE_SENSITIVE = false;
    private static final boolean DEFAULT_WHOLE_WORD = false;
    private static final int BUFFER_SIZE = 1024;
    private static final String EOLN = "\r\n";

    public static List<String> readSentences(InputStream stream) throws IOException {

        byte[] buffer = new byte[BUFFER_SIZE];
        List<String> list = new ArrayList<>();

        int lengthBuffer = 0;
        int offsetBuffer = 0;
        int offset = 0;
        int count;

        while ((count = stream.read(buffer, offsetBuffer, buffer.length - offsetBuffer)) != -1) {

            lengthBuffer = offsetBuffer + count;
            int posEOLN;
            offset = 0;

            while ((posEOLN = scanBufferEOLN(buffer, offset, lengthBuffer)) != -1) {
                list.add(new String(buffer, offset, posEOLN - offset));
                offset = posEOLN + EOLN.length();
            }

            offsetBuffer = lengthBuffer - offset;
            System.arraycopy(buffer, offset, buffer, 0, offsetBuffer);
        }
        if (lengthBuffer != offset) {
            list.add(new String(buffer, offset, lengthBuffer - offset));
        }
        return list;
    }

    public static List<String> readSentences(String fileName) throws IOException {
        InputStream stream = new FileInputStream(fileName);
        try {
            return readSentences(stream);
        } finally {
            stream.close();
        }
    }

    public static List<String> filter(List<String> sentences, String searchString, boolean caseSensitive) {
        List<String> list = new ArrayList<>();
        String normSearchString = caseSensitive ? searchString : searchString.toLowerCase();
        if (sentences instanceof Iterable) {
            for (String sentence : (Iterable<String>) sentences) {
                String normSentence = caseSensitive ? sentence : sentence.toLowerCase();
                int index = normSentence.indexOf(normSearchString);
                if ((index != -1) && (!DEFAULT_WHOLE_WORD || isWholeWord(normSentence, normSearchString, index))) {
                    list.add(sentence);
                }
            }
        } else {
            throw new UnsupportedOperationException("The only iterable classes are supported");
        }
        return list;
    }

    public static int countOccurance(List<String> sentences, String searchString, boolean caseSensitive) {
        int countWord = 0;
        String normSearchString = caseSensitive ? searchString : searchString.toLowerCase();
        if (sentences instanceof Iterable) {
            for (String sentence : (Iterable<String>) sentences) {
                String normSentence = caseSensitive ? sentence : sentence.toLowerCase();
                int offset = 0;
                int index;
                while ((index = normSentence.indexOf(normSearchString, offset)) != -1) {
                    if (!DEFAULT_WHOLE_WORD || isWholeWord(normSentence, normSearchString, index)) {
                        countWord++;
                    }
                    offset = index + normSearchString.length();
                }
            }
        } else {
            throw new UnsupportedOperationException("The only iterable classes are supported");
        }
        return countWord;
    }

    public static void printSentences(List<String> sentences) {
        if (sentences instanceof Iterable) {
            for (String sentence : (Iterable<String>) sentences) {
                System.out.println(sentence);
            }
        } else {
            throw new UnsupportedOperationException("The only iterable classes are supported");
        }
    }

    private static int scanBufferEOLN(byte[] buffer, int offset, int length) {
        for (int i = offset; i < (length - EOLN.length() + 1); i++) {
            boolean isMatched = true;
            for (int j = 0; j < EOLN.length(); j++) {
                isMatched = isMatched && (buffer[i + j] == EOLN.charAt(j));
                if (!isMatched) {
                    break;
                }
            }
            if (isMatched) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isWholeWord(String sentence, String searchString, int index) {
        return (index == 0 || " ".contains(sentence.substring(index - 1, index)))
                && (index >= sentence.length() - searchString.length() || "  .?!".contains(sentence.substring(index + searchString.length(), index + searchString.length() + 1))
        );
    }

    public static void main(String[] args) {

        String fileName = "C:\\Users\\acc\\IdeaProjects\\javacourses\\TestOOP\\src\\com\\avramenko\\io\\FileAnalyzer.java";
        String word = "public";
//        String fileName = args.length > 0 ? args[0] : null;
        //      String word = args.length > 1 ? args[1] : null;
        if (fileName == null) {
            System.out.println("ERROR: A file name is not specified");
        }
        if (word == null) {
            System.out.println("ERROR: A word is not specified");
        }
        if (fileName == null || word == null) {
            System.out.println("Usage:");
            System.out.println("java FileAnalyzer <FILENAME> <WORD>");
        } else {
            try {
                List<String> sentences = readSentences(fileName);
                List<String> filteredSentences = filter(sentences, word, DEFAULT_CASE_SENSITIVE);
                printSentences(filteredSentences);
                System.out.println("Couunt of \"" + word + "\": " + countOccurance(filteredSentences, word, DEFAULT_CASE_SENSITIVE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}