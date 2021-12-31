package net.alexapps.jardup;

import net.alexapps.jardup.util.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import static net.alexapps.jardup.util.Utils.sorted;

public class FolderFinder {
    public static ArrayList<String> find(Settings settings) {
        HashSet<String> folders = new HashSet<>();
        for (String root : settings.getRoots()) {
            find(new File(root), folders);
        }
        return sorted(folders);
    }

    private static void find(File root, HashSet<String> folders) {
        if (!root.isDirectory())
            return;

        if (!folders.add(root.getPath()))
            return;

        File[] files = root.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                find(file, folders);
            }
        }
    }
}
