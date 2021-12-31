package net.alexapps.jardup;

import net.alexapps.jardup.data.ClassInfo;
import net.alexapps.jardup.data.DupInfo;
import net.alexapps.jardup.util.Settings;
import net.alexapps.jardup.util.Logger;
import net.alexapps.jardup.util.Utils;

import java.io.IOException;
import java.util.*;

public class App {
    private final Settings _settings;
    private final Logger _logger;

    public App(Settings settings, Logger logger) {
        _settings = settings;
        _logger = logger;
    }

    public void run() {
        Collection<String> folders = FolderFinder.find(_settings);
        Collection<String> jars = new JarFinder(_logger, _settings).find(folders);
        Collection<ClassInfo> classes = ClassFinder.find(jars);
        Collection<DupInfo> dupInfos = DupFinder.find(classes, _logger);
        DupReporter.report(dupInfos, _logger);
    }

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings(args);
        try (Logger logger = new Logger(Utils.tempFile())) {
            App app = new App(settings, logger);
            app.run();
            logger.both("Done, more details in " + logger.getLocation());
        }
    }
}
