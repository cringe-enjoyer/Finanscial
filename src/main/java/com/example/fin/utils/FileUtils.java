package com.example.fin.utils;

import com.example.fin.MainApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class FileUtils {
    public static final String PATH = setPath();

    /**
     * Returns true if file is empty
     *
     * @param file file
     * @return true if the file is empty otherwise return false
     */
    public static boolean isEmpty(File file) {
        if (file.exists()) {
            try (FileInputStream reader = new FileInputStream(file)) {
                if (reader.readAllBytes().length > 0)
                    return false;
            } catch (IOException ex) {
                System.out.println("isEmpty " + ex.getMessage());
            }
        }
        return true;
    }

    /**
     * Sets path to user's data
     * @return path to file with user's data
     */
    private static String setPath() {
        URL path = MainApplication.class.getResource("saveData/data.txt");
        if (path == null) {
            return "data.txt";
        }
        return path.getFile();
    }
}
