package net.alexapps.jardup;

import net.alexapps.jardup.data.ClassInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassFinder {
    private static final Pattern PATTERN = Pattern.compile("^.*\\.class$");

    public static ArrayList<ClassInfo> find(Collection<String> jars) {
        ArrayList<ClassInfo> classInfos = new ArrayList<>();
        for (String jar : jars) {
            find(jar, classInfos);
        }
        return classInfos;
    }

    private static void find(String jar, ArrayList<ClassInfo> classInfos) {
        ZipFile zipFile;

        try {
            zipFile = new ZipFile(jar);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        zipFile
                .stream()
                .filter(ClassFinder::matches)
                .forEach(entry -> classInfos.add(new ClassInfo(jar, entry.getName())));
    }

    private static boolean matches(ZipEntry entry) {
        if (entry.isDirectory())
            return false;

        Matcher matcher = PATTERN.matcher(entry.getName());
        return matcher.matches();
    }
}
