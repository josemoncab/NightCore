package dev.josemc.nightcore.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static boolean create(File file) {
        if (file.exists()) return false;

        File parent = file.getParentFile();
        if (parent == null) return false;

        parent.mkdirs();

        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
