package net.alexapps.jardup.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class Settings {
    private static final String OPT_EXCLUDE_DIR = "--exclude-dir";
    private static final String OPT_EXCLUDE_JAR = "--exclude-jar";
    private static final String OPT_EXCLUDE_CLASS = "--exclude-class";

    private final ArrayList<String> _roots;
    private final ArrayList<Pattern> _excludeDirRegExes;
    private final ArrayList<Pattern> _excludeJarRegExes;
    private final ArrayList<Pattern> _excludeClassRegExes;

    public Settings(String... args) {
        _roots = new ArrayList<>();
        _excludeDirRegExes = new ArrayList<>();
        _excludeJarRegExes = new ArrayList<>();
        _excludeClassRegExes = new ArrayList<>();

        for (String arg : args) {
            if (arg.startsWith(OPT_EXCLUDE_JAR + "=")) {
                String pattern = arg.substring(OPT_EXCLUDE_JAR.length() + 1);
                _excludeJarRegExes.add(Pattern.compile(pattern));
            } else if (arg.startsWith(OPT_EXCLUDE_DIR + "=")) {
                String pattern = arg.substring(OPT_EXCLUDE_DIR.length() + 1);
                _excludeDirRegExes.add(Pattern.compile(pattern));
            } else if (arg.startsWith(OPT_EXCLUDE_CLASS + "=")) {
                String pattern = arg.substring(OPT_EXCLUDE_CLASS.length() + 1);
                _excludeClassRegExes.add(Pattern.compile(pattern));
            } else {
                _roots.add(arg);
            }
        }

        if (_roots.isEmpty())
            _roots.add(Utils.workingDir());
    }

    public Collection<String> getRoots() {
        return _roots;
    }

    private boolean matches(String value, ArrayList<Pattern> patterns) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean excludeJar(String jar) {
        return matches(jar, _excludeJarRegExes);
    }

    public boolean excludeDir(String dir) {
        return matches(dir, _excludeDirRegExes);
    }

    public boolean excludeClass(String cls) {
        return matches(cls, _excludeClassRegExes);
    }
}
