package net.alexapps.jardup;

import net.alexapps.jardup.data.ClassInfo;
import net.alexapps.jardup.data.DupInfo;
import net.alexapps.jardup.util.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static net.alexapps.jardup.util.Utils.LIST_SEPARATOR;
import static net.alexapps.jardup.util.Utils.sorted;

public class DupFinder {
    public static Collection<DupInfo> find(Collection<ClassInfo> classes, Logger logger) {
        HashMap<String, ArrayList<ClassInfo>> classNameToClassInfos = organizeByClassName(classes);
        ArrayList<ArrayList<ClassInfo>> dupsGroupedByClassname = filterOutOkays(classNameToClassInfos);
        logger.both("Found " + dupsGroupedByClassname.size() + " DUP classes (total)");
        HashMap<String, ArrayList<String>> jarListToClassNames = collectProblematicJars(dupsGroupedByClassname);
        return asDups(jarListToClassNames);
    }

    private static Collection<DupInfo> asDups(HashMap<String, ArrayList<String>> jarListToClassNames) {
        ArrayList<DupInfo> dupInfos = new ArrayList<>();

        for (String jarList : sorted(jarListToClassNames.keySet())) {
            dupInfos.add(new DupInfo(jarList, jarListToClassNames.get(jarList)));
        }
        return dupInfos;
    }

    private static HashMap<String, ArrayList<ClassInfo>> organizeByClassName(Collection<ClassInfo> classes) {
        HashMap<String, ArrayList<ClassInfo>> classNameToClassInfos = new HashMap<>();
        classes.forEach(classInfo -> {
            ArrayList<ClassInfo> infos = classNameToClassInfos.computeIfAbsent(
                    classInfo.getClassName(),
                    k -> new ArrayList<>(1)
            );
            infos.add(classInfo);
        });
        return classNameToClassInfos;
    }

    private static ArrayList<ArrayList<ClassInfo>> filterOutOkays(HashMap<String, ArrayList<ClassInfo>> classNameToClassInfos) {
        ArrayList<ArrayList<ClassInfo>> dupsGroupedByClassname = new ArrayList<>();
        for (ArrayList<ClassInfo> classInfos : classNameToClassInfos.values()) {
            if (classInfos.size() > 1) {
                dupsGroupedByClassname.add(classInfos);
            }
        }
        return dupsGroupedByClassname;
    }

    private static HashMap<String, ArrayList<String>> collectProblematicJars(ArrayList<ArrayList<ClassInfo>> dups) {
        HashMap<String, ArrayList<String>> jarListToClassNames = new HashMap<>();
        for (ArrayList<ClassInfo> classInfos : dups) {
            ArrayList<String> jars = new ArrayList<>(classInfos.size());
            for (ClassInfo classInfo : classInfos) {
                jars.add(classInfo.getJarPath());
            }
            String problematicJarsList = String.join(LIST_SEPARATOR, sorted(jars));

            ArrayList<String> classNames = jarListToClassNames.computeIfAbsent(problematicJarsList, k -> new ArrayList<>(1));
            classNames.add(classInfos.get(0).getClassName());
        }
        return jarListToClassNames;
    }
}
