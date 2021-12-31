package net.alexapps.jardup.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger implements AutoCloseable {
    private final PrintWriter _writer;
    private final String _location;
    private int _stdoutLimit = Integer.MAX_VALUE;
    private String _stdoutLimitText;

    public Logger(File file) throws FileNotFoundException {
        _location = file.getAbsolutePath();
        _writer = new PrintWriter(file);
    }

    public void file(String message) {
        _writer.println(message);
    }

    public void stdout(String message) {
        System.out.println(message);
    }

    public void both(String message) {
        file(message);

        if (_stdoutLimit > 0)
            stdout(message);
        else if (_stdoutLimit == 0)
            stdout(_stdoutLimitText);
        --_stdoutLimit;
    }

    @Override
    public void close() {
        _writer.close();
    }

    public String getLocation() {
        return _location;
    }

    public void setStdoutLimit(int maxLines, String limitText) {
        _stdoutLimit = maxLines;
        _stdoutLimitText = limitText;
    }

    public void resetStdoutLimit() {
        _stdoutLimit = Integer.MAX_VALUE;
        _stdoutLimitText = "";
    }
}
