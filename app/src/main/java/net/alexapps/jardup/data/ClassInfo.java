package net.alexapps.jardup.data;

public class ClassInfo {
    private final String _jarPath;
    private final String _className;

    public ClassInfo(String jarPath, String className) {
        _jarPath = jarPath;
        _className = className;
    }

    public String getJarPath() {
        return _jarPath;
    }

    public String getClassName() {
        return _className;
    }
}
