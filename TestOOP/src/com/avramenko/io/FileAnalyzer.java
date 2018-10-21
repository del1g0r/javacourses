package com.avramenko.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileAnalyzer {

    private static final int BUFFER_SIZE = 1024;
    private static final String EOLN = "\r\n";
    private static final boolean DEFAULT_CASE_SENSITIVE = false;
    private static final boolean DEFAULT_WHOLE_WORD = false;

    public static int scanFile(String fileName, String word, boolean caseSensitive) throws IOException {
        InputStream from = new FileInputStream(fileName);
        byte[] buffer = new byte[BUFFER_SIZE];

        int lengthBuffer = 0;
        int offsetBuffer = 0;
        int offset = 0;
        int count;
        int countWord = 0;

        while ((count = from.read(buffer, offsetBuffer, buffer.length - offsetBuffer)) != -1) {

            lengthBuffer = offsetBuffer + count;
            int posEOLN;
            offset = 0;

            while ((posEOLN = scanBufferEOLN(buffer, offset, lengthBuffer)) != -1) {
                countWord += scanLine(new String(buffer, offset, posEOLN - offset), word, caseSensitive);
                offset = posEOLN + EOLN.length();
            }

            offsetBuffer = lengthBuffer - offset;
            System.arraycopy(buffer, offset, buffer, 0, offsetBuffer);
        }
        if (lengthBuffer != offset) {
            countWord += scanLine(new String(buffer, offset, lengthBuffer - offset), word, caseSensitive);
        }
        return countWord;
    }

    private static int scanLine(String line, String word, boolean caseSensitive) {
        int countWord = 0;
        int offset = 0;
        int index;
        String normLine = caseSensitive ? line : line.toLowerCase();
        String normWord = caseSensitive ? word : word.toLowerCase();
        while ((index = normLine.indexOf(normWord, offset)) != -1) {
            if (!DEFAULT_WHOLE_WORD || isWholeWord(normLine, normWord, index)) {
                countWord++;
            }
            offset = index + normWord.length();
        }
        if (countWord > 0) {
            System.out.println(line);
        }
        return countWord;
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

    private static boolean isWholeWord(String line, String word, int index) {
        return (index == 0 || " ".contains(line.substring(index - 1, index)))
                && (index >= line.length() - word.length() || "  .?!".contains(line.substring(index + word.length(), index + word.length() + 1))
        );
    }

    public static void main(String[] args) {

//        String filePath = "C:\\Users\\acc\\IdeaProjects\\javacourses\\TestOOP\\src\\com\\avramenko\\io\\FileAnalyzer.java";
//        String word = "public";
        String filePath = args.length > 0 ? args[0] : null;
        String word = args.length > 1 ? args[1] : null;
        if (filePath == null) {
            System.out.println("ERROR: A file name is not specified");
        }
        if (word == null) {
            System.out.println("ERROR: A word is not specified");
        }
        if (filePath == null || word == null) {
            System.out.println("Usage:");
            System.out.println("java FileAnalyzer <FILENAME> <WORD>");
        } else {
            try {
                System.out.println("Couunt of \"" + word + "\": " + scanFile(filePath, word, DEFAULT_CASE_SENSITIVE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}