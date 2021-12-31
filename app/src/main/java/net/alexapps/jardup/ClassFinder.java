package net.alexapps.jardup;

import net.alexapps.jardup.data.ClassInfo;
import net.alexapps.jardup.util.Logger;
import net.alexapps.jardup.util.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassFinder {
    private static final Pattern PATTERN = Pattern.compile("^.*\\.class$");

    private final Logger _logger;
    private final Settings _settings;

    public ClassFinder(Logger logger, Settings settings) {
        _logger = logger;
        _settings = settings;
    }

    public ArrayList<ClassInfo> find(Collection<String> jars) {
        ArrayList<ClassInfo> classInfos = new ArrayList<>();
        for (String jar : jars) {
            find(jar, classInfos);
        }
        return classInfos;
    }

    private void find(String jar, ArrayList<ClassInfo> classInfos) {
        ZipFile zipFile;

        try {
            zipFile = new ZipFile(jar);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        _logger.setStdoutLimit(5, "...");
        zipFile
                .stream()
                .filter(ClassFinder::matches)
                .forEach(entry -> addClass(jar, entry.getName(), classInfos));
        _logger.resetStdoutLimit();
    }

    private void addClass(String jar, String className, ArrayList<ClassInfo> classInfos) {
        if (_settings.excludeClass(className)) {
            _logger.both("Excluded: " + jar + ", " + className);
        } else {
            classInfos.add(new ClassInfo(jar, className));
        }
    }

    private static boolean matches(ZipEntry entry) {
        if (entry.isDirectory())
            return false;

        Matcher matcher = PATTERN.matcher(entry.getName());
        return matcher.matches();
    }
}
