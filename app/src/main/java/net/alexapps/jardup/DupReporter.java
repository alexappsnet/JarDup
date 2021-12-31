package net.alexapps.jardup;

import net.alexapps.jardup.data.DupInfo;
import net.alexapps.jardup.util.Logger;

import java.util.Collection;
import java.util.Iterator;

public class DupReporter {
    public static void report(Collection<DupInfo> dupInfos, Logger logger) {
        dupInfos.forEach(dupInfo -> {
            logger.both("DUP classes in jars");
            dupInfo.getJars().forEach(jar -> logger.both("  " + jar));
            Collection<String> classNames = dupInfo.getClassNames();

            int count = classNames.size();
            Iterator<String> it = classNames.iterator();
            logger.setStdoutLimit(3, "    ...");
            while (it.hasNext()) {
                logger.both("    " + it.next());
            }
            logger.resetStdoutLimit();
            logger.both("    (" + count + " DUB classes)");
        });
    }
}
