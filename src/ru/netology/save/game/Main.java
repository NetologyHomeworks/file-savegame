package ru.netology.save.game;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String ROOT_DIR = "Games";

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 10, 1, 15.5);
        GameProgress gameProgress2 = new GameProgress(50, 5, 3, 100.5);
        GameProgress gameProgress3 = new GameProgress(10, 2, 8, 250.3);

        saveGame("savegames//save1.dat", gameProgress1);
        saveGame("savegames//save2.dat", gameProgress2);
        saveGame("savegames//save3.dat", gameProgress3);

        zipFiles("savegames//save.zip", Arrays.asList(
                "savegames//save1.dat",
                "savegames//save2.dat",
                "savegames//save3.dat"));
    }

    private static void saveGame(String saveFile, GameProgress progress) {
        try (FileOutputStream fileOutStream = new FileOutputStream(ROOT_DIR + "//" + saveFile);
             ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream)) {
            objOutStream.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String zipName, List<String> nameList) {
        try (ZipOutputStream zipOutStream = new ZipOutputStream(new FileOutputStream(ROOT_DIR + "//" + zipName))) {
            for (String item : nameList) {
                String zipFileName = ROOT_DIR + "//" + item;
                try (FileInputStream fileInpStream = new FileInputStream(zipFileName)) {
                    ZipEntry entry = new ZipEntry(new File(zipFileName).getName());
                    zipOutStream.putNextEntry(entry);
                    while (fileInpStream.available() > 0) {
                        zipOutStream.write(fileInpStream.read());
                    }
                    zipOutStream.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        clearSaves(nameList);
    }

    private static void clearSaves(List<String> nameList) {
        for (String item : nameList) {
            String fileName = ROOT_DIR + "//" + item;
            File file = new File(fileName);
            if (file.exists()) file.delete();
        }
    }
}
