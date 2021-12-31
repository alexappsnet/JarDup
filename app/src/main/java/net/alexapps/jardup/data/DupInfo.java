package net.alexapps.jardup.data;

import net.alexapps.jardup.util.Utils;

import java.util.Arrays;
import java.util.Collection;

import static net.alexapps.jardup.util.Utils.sorted;

public class DupInfo {
    private final Collection<String> _jars;
    private final Collection<String> _classNames;

    public DupInfo(String jarList, Collection<String> classNames) {
        _jars = Arrays.asList(jarList.split(Utils.LIST_SEPARATOR));
        _classNames = sorted(classNames);
    }

    public Collection<String> getJars() {
        return _jars;
    }

    public Collection<String> getClassNames() {
        return _classNames;
    }
}
