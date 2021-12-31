package net.alexapps.jardup.util;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class Utils {
    public static final String LIST_SEPARATOR = "#";

    public static String workingDir() {
        return System.getProperty("user.dir");
    }

    public static File tempFile() throws IOException {
        File folder = new File(tempFolder());
        return File.createTempFile("jardup_", ".out", folder);
    }

    @SafeVarargs
    public static <T> T nonNull(@Nonnull Supplier<T> ...suppliers) {
        for (Supplier<T> supplier : suppliers) {
            T result = supplier.get();
            if (result != null)
                return result;
        }
        throw new NullPointerException();
    }

    public static String tempFolder() {
        return nonNull(
                () -> System.getenv("TEMP"),
                () -> System.getenv("TMP"),
                Utils::workingDir
        );
    }

    public static ArrayList<String> sorted(Collection<String> c) {
        ArrayList<String> copy = new ArrayList<>(c);
        copy.sort(String::compareTo);
        return copy;
    }
}
