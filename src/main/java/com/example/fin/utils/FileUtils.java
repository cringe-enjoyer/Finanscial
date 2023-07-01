package com.example.fin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    public static final String PATH = "/data.txt";

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

            }
        }
        return true;
    }
}
