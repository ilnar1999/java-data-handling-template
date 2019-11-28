package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        String  fullPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + path;
        File file = new File(fullPath);
        String[] filesAndDirectories = file.list();
        long count = 0;
        if (filesAndDirectories != null) {
            for (String fileOrDirectory: filesAndDirectories) {
                if (new File(fullPath + File.separator + fileOrDirectory).isFile()) {
                    count++;
                } else {
                    count += countFilesInDirectory(path + File.separator + fileOrDirectory);
                }
            }
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        String  fullPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + path;
        File file = new File(fullPath);
        String[] filesAndDirectories = file.list();
        long count = 1;
        if (filesAndDirectories != null) {
            for (String fileOrDirectory: filesAndDirectories) {
                if (new File(fullPath + File.separator + fileOrDirectory).isDirectory()) {
                    count += countDirsInDirectory(path + File.separator + fileOrDirectory);
                }
            }
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);
        String[] filesInFrom = fromFile.list();
        File newFile;
        System.out.println(filesInFrom.length);
        for (String file: filesInFrom) {
            String text = "";
            if (file.endsWith(".txt") && new File(from + "/" + file).isFile()) {
                newFile = new File(to + "/" + file);
                if (!newFile.exists()) {
                    try (FileReader reader = new FileReader(from + "/" + file); FileWriter writer = new FileWriter(newFile)) {
                        newFile.createNewFile();
                        while (reader.ready()) {
                            text += (char)reader.read();
                        }
                        writer.write(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return;
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        File dir = new File(resource.getPath());
        if (!name.endsWith(".txt")) {
            name += ".txt";
        }
        File file = new File(dir, name);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String path = "src" + File.separator + "main" + File.separator + "resources";
        String content = "";
        File file = new File(path, fileName);
        try (FileReader fileReader = new FileReader(file)) {
            while (fileReader.ready()) {
                content += (char)fileReader.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
