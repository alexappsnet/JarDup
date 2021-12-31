/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package net.alexapps.jardup.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsTest {
    @Test public void readRootsFromArgs() {
        Settings settings = new Settings("folder1", "folder2");
        assertArrayEquals(new String[] {"folder1", "folder2"}, settings.getRoots().toArray());
    }

    @Test public void excludeJars() {
        Settings settings = new Settings("--exclude-jar=.bcd[0-9].jar");
        assertFalse(settings.excludeJar("abcd1"));
        assertFalse(settings.excludeJar("abcd"));
        assertFalse(settings.excludeJar("bcd1"));
        assertTrue(settings.excludeJar("abcd1.jar"));
        assertFalse(settings.excludeJar("bcd.jar"));
    }

    @Test public void excludeDirs() {
        Settings settings = new Settings("--exclude-dir=dirA.*");
        assertFalse(settings.excludeDir("dirB"));
        assertTrue(settings.excludeDir("dirA"));
        assertTrue(settings.excludeDir("dirA1"));
        assertTrue(settings.excludeDir("dirA2"));
    }

    @Test public void excludeClasses() {
        Settings settings = new Settings("--exclude-class=.*/Test.class");
        assertTrue(settings.excludeClass("net/alexapps/jardup/Test.class"));
        assertTrue(settings.excludeClass("net/alexapps/Test.class"));
        assertFalse(settings.excludeClass("net/alexapps/jardup/Foo.class"));
        assertFalse(settings.excludeClass("Bar.class"));
        assertFalse(settings.excludeClass("/com/company1/Bar.class"));
    }
}
