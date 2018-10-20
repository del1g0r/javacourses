package com.avramenko.io;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class FileManager {

    public static final int BUFFER_SIZE = 1024;

    // public static int countFiles(String path) - принимает путь к папке,
    // возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) {
        int cnt = 0;
        File root = new File(path);
        if (root.exists()) {
            for (File file : root.listFiles()) {
                if (file.isDirectory()) {
                    cnt += countFiles(file.getAbsolutePath());
                }
                if (file.isFile()) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    // public static int countDirs(String path) - принимает путь к папке,
    // возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) {
        int cnt = 0;
        File root = new File(path);
        if (root.exists()) {
            for (File file : root.listFiles()) {
                if (file.isDirectory()) {
                    cnt += countDirs(file.getAbsolutePath());
                    cnt++;
                }
            }
        }
        return cnt;
    }

    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    // метод по копированию папок и файлов.
    public static void copy(String from, String to) throws IOException {
        File fromFile = new File(from);
        File toFile = new File(to);

        if (toFile.exists()) {
            throw new FileAlreadyExistsException("The file/directory is already exists: " + toFile.getAbsolutePath());
        }
        if (fromFile.isDirectory()) {
            toFile.mkdir();
            for (File file : fromFile.listFiles()) {
                copy(file.getAbsolutePath(), toFile.getAbsolutePath() + File.separatorChar + file.getName());
            }
        }
        if (fromFile.isFile()) {
            copyFile(fromFile.getAbsolutePath(), toFile.getAbsolutePath());
        }
    }

    //    Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться перемещение.
    // метод по перемещению папок и файлов.
    public static void move(String from, String to) throws IOException {
        File fromFile = new File(from);
        File toFile = new File(to);

        if (toFile.exists()) {
            throw new FileAlreadyExistsException("The file/directory is already exists: " + toFile.getAbsolutePath());
        }

        if (!fromFile.renameTo(toFile)) {
            throw new IOException("The file " + from + " could not be renamed to " + to);
        }
    }


    public static void copyFile(InputStream from, OutputStream to) throws IOException {

        byte[] buffer = new byte[BUFFER_SIZE];

        int count;

        while ((count = from.read(buffer)) != -1) {
            to.write(buffer, 0, count);
        }
    }

    public static void copyFile(String from, String to) throws IOException {
        InputStream fromStream = new FileInputStream(from);
        OutputStream toStream = new FileOutputStream(to);
        try {
            //System.out.println("COPY " + from + " TO " + to);
            copyFile(fromStream, toStream);
        } finally {
            fromStream.close();
            toStream.close();
        }
    }

    public static class Test {

        public static void main(String[] args) {

            String projectPath = "C:\\Users\\acc\\IdeaProjects\\javacourses\\TestOOP";

            System.out.println("Dirs :" + countDirs(projectPath));
            System.out.println("Files :" + countFiles(projectPath));

            String backupPath = "C:\\Backup\\TestOOP";

            try {
                copy(projectPath, backupPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String archivePath = "C:\\Backup\\TestOOP_arc";
            try {
                move(backupPath, archivePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}