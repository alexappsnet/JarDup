package net.alexapps.jardup;

import net.alexapps.jardup.util.Logger;
import net.alexapps.jardup.util.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.alexapps.jardup.util.Utils.sorted;

public class JarFinder {
    private static final Pattern PATTERN = Pattern.compile("^.*\\.(jar|zip)$", Pattern.CASE_INSENSITIVE);
    private final Logger _logger;
    private final Settings _settings;

    public JarFinder(Logger logger, Settings settings) {
        _logger = logger;
        _settings = settings;
    }

    public ArrayList<String> find(Collection<String> folders) {
        _logger.both("Searching for jars...");
        ArrayList<String> jars = new ArrayList<>();

        for (String folder : folders) {
            find(new File(folder), jars);
        }

        _logger.both("  (" + jars.size() + " jars found)");
        return sorted(jars);
    }

    private void find(File root, Collection<String> jars) {
        _logger.both("  in " + root);

        File[] files = root.listFiles();
        if (files == null)
            return;

        _logger.setStdoutLimit(3, "    ...");
        for (File file : files) {
            if (matches(file)) {
                addJar(file, jars);
            }
        }
        _logger.resetStdoutLimit();
    }

    private void addJar(File jar, Collection<String> jars) {
        if (_settings.excludeJar(jar.getName())) {
            String message = "    Excluded: " + jar;
            _logger.file(message);
            _logger.stdout(message);
        } else {
            _logger.both("    " + jar);
            jars.add(jar.getPath());
        }
    }

    private boolean matches(File file) {
        if (!file.isFile())
            return false;

        Matcher matcher = PATTERN.matcher(file.getName());
        return matcher.matches();
    }
}
