package net.alexapps.jardup;

import net.alexapps.jardup.util.Logger;
import net.alexapps.jardup.util.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import static net.alexapps.jardup.util.Utils.sorted;

public class FolderFinder {
    private final Logger _logger;
    private final Settings _settings;

    public FolderFinder(Logger logger, Settings settings) {
        _logger = logger;
        _settings = settings;
    }

    public ArrayList<String> find() {
        _logger.setStdoutLimit(5, "...");
        HashSet<String> folders = new HashSet<>();
        for (String root : _settings.getRoots()) {
            find(new File(root), folders);
        }
        _logger.resetStdoutLimit();
        return sorted(folders);
    }

    private void find(File root, HashSet<String> folders) {
        if (!root.isDirectory())
            return;

        if (_settings.excludeDir(root.getName())) {
            _logger.both("Excluded: " + root);
            return;
        }

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
