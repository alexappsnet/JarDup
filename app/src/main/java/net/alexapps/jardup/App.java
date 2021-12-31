package net.alexapps.jardup;

import net.alexapps.jardup.data.ClassInfo;
import net.alexapps.jardup.data.DupInfo;
import net.alexapps.jardup.util.ArgParser;
import net.alexapps.jardup.util.Logger;
import net.alexapps.jardup.util.Utils;

import java.io.IOException;
import java.util.*;

public class App {
    private final Collection<String> _roots;
    private final Logger _logger;

    public App(Collection<String> roots, Logger logger) {
        _roots = roots;
        _logger = logger;
    }

    public void run() {
        Collection<String> folders = FolderFinder.find(_roots);
        Collection<String> jars = JarFinder.find(folders, _logger);
        Collection<ClassInfo> classes = ClassFinder.find(jars);
        Collection<DupInfo> dupInfos = DupFinder.find(classes, _logger);
        DupReporter.report(dupInfos, _logger);
    }

    public static void main(String[] args) throws IOException {
        ArgParser argParser = new ArgParser(args);
        try (Logger logger = new Logger(Utils.tempFile())) {
            App app = new App(argParser.getRoots(), logger);
            app.run();
            logger.both("Done, more details in " + logger.getLocation());
        }
    }
}
