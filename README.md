# What does it do

Searches recursively for all classes in all jar files and reports the duplicates.

# How to run

```
$ java -jar jardup.jar .
```

or

```
$ java -jar jardup.jar folder1 folder2
```

# Output example

```text
Searching for jars...
  in .
    .\javafx-src.zip
    .\src.zip
  in .\bin
  in .\db
  in .\db\bin
  in .\db\lib
    .\db\lib\derby.jar
    .\db\lib\derbyclient.jar
    .\db\lib\derbyLocale_cs.jar
    ...
  in .\lib\visualvm\platform\lib\locale
    .\lib\visualvm\platform\lib\locale\boot_ja.jar
    .\lib\visualvm\platform\lib\locale\boot_zh_CN.jar
    .\lib\visualvm\platform\lib\locale\org-openide-modules_ja.jar
    ...
  in .\lib\visualvm\platform\modules
    .\lib\visualvm\platform\modules\org-netbeans-api-annotations-common.jar
    .\lib\visualvm\platform\modules\org-netbeans-api-progress.jar
    .\lib\visualvm\platform\modules\org-netbeans-api-search.jar
...
Found 451 DUP classes (total)
...
DUP classes in jars
  .\lib\missioncontrol\plugins\org.eclipse.osgi.services_3.4.0.v20140312-2051.jar
  .\lib\missioncontrol\plugins\org.eclipse.osgi_3.10.1.v20140909-1633.jar
    org/osgi/service/log/package-info.class
    (1 DUB classes)
DUP classes in jars
  .\lib\visualvm\profiler\lib\jfluid-server.jar
  .\lib\visualvm\profiler\modules\org-netbeans-lib-profiler.jar
    org/netbeans/lib/profiler/global/CalibrationDataFileIO.class
    org/netbeans/lib/profiler/global/CommonConstants.class
    org/netbeans/lib/profiler/global/InstrumentationFilter.class
    ...
    (37 DUB classes)
Done, more details in /tmp/jardup_2216494129843002744.out
```
