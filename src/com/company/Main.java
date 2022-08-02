package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static final String path = "./Game";

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(99, 10, 2, 321.12);
        GameProgress game2 = new GameProgress(89, 2, 1, 209.12);
        GameProgress game3 = new GameProgress(79, 7, 4, 560.12);

        List<String> savePaths = new ArrayList<>();

        saveGame(path + "/save1.dat", game1);
        savePaths.add(path + "/save1.dat");

        saveGame(path + "/save2.dat", game2);
        savePaths.add(path + "/save2.dat");

        saveGame(path + "/save3.dat", game3);
        savePaths.add(path + "/save3.dat");

        zipFiles(path + "/save.zip", savePaths);

        for (String path : savePaths) {
            File file = new File(path);
            file.delete();
        }

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public static void zipFiles(String path, List<String> savePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path))) {
            for (String savePath : savePaths) {
                try {
                    FileInputStream fis = new FileInputStream(savePath);
                    ZipEntry entry = new ZipEntry(savePath);
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
