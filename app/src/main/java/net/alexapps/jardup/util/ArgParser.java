package net.alexapps.jardup.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

public class ArgParser {
    private final ArrayList<String> _roots;

    public ArgParser(String... args) {
        _roots = new ArrayList<>();
        if (args.length == 0)
            _roots.add(Utils.workingDir());
        else
            _roots.addAll(Arrays.asList(args));
    }

    public Collection<String> getRoots() {
        return _roots;
    }
}
