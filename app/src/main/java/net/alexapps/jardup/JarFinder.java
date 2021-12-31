package net.alexapps.jardup;

import net.alexapps.jardup.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.alexapps.jardup.util.Utils.sorted;

public class JarFinder {
    private static final Pattern PATTERN = Pattern.compile("^.*\\.(jar|zip)$", Pattern.CASE_INSENSITIVE);

    public static ArrayList<String> find(Collection<String> folders, Logger logger) {
        logger.both("Searching for jars...");
        ArrayList<String> jars = new ArrayList<>();

        for (String folder : folders) {
            find(new File(folder), jars, logger);
        }

        logger.both("  (" + jars.size() + " jars found)");
        return sorted(jars);
    }

    private static void find(File root, Collection<String> jars, Logger logger) {
        logger.both("  in " + root);

        File[] files = root.listFiles();
        if (files == null)
            return;

        logger.setStdoutLimit(3, "    ...");
        for (File file : files) {
            if (matches(file)) {
                logger.both("    " + file);
                jars.add(file.getPath());
            }
        }
        logger.resetStdoutLimit();
    }

    private static boolean matches(File file) {
        if (!file.isFile())
            return false;

        Matcher matcher = PATTERN.matcher(file.getName());
        return matcher.matches();
    }
}
