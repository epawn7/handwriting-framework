package jvm.entry;

import java.io.File;
import java.io.IOException;
import jvm.command.Command;

/**
 * @author jinfan 2022-07-21
 */
public class ClassPath {

    public static final String pathListSeparator = System.getProperty("os.name").contains("Windows") ? ";" : ":";
    /**
     * jre的class path
     */
    private Entry bootClasspath;

    private Entry extClasspath;

    /**
     * 程序自己的class path
     */
    private Entry userClasspath;

    public ClassPath(Command command) {
        bootClasspath = parseBootClassPath(command.getClassPath());
        extClasspath = parseExtClasspath(command.getClassPath());
        userClasspath = parseUserClasspath(command.getUserClassPath());
    }

    public byte[] readClass(String className){

        byte[] bytes = bootClasspath.readClass(className);
        return bytes;
    }


    private Entry parseBootClassPath(String jrePath) {
        //可能出现的情况是: jre/lib/*
        String jreLibPath = jrePath + File.separator + "lib" + File.separator + "*";
        return new WildcardEntry(jreLibPath);
    }

    private Entry parseExtClasspath(String jrePath) {
        //可能出现的情况是: jre/lib/*
        String jreExtPath = jrePath + File.separator + "lib" + File.separator + "ext" + File.separator + "*";
        return new WildcardEntry(jreExtPath);
    }

    private Entry parseUserClasspath(String jrePath) {
        if (jrePath == null) {
            return null;
        }
        String jreExtPath = jrePath + File.separator + "lib" + File.separator + "ext" + File.separator + "*";
        return createEntry(jreExtPath);
    }

    private Entry createEntry(String path) {
        if (path != null) {
            if (path.contains(pathListSeparator)) {
                return new CompositeEntry(path);
            } else if (path.contains("*")) {
                return new WildcardEntry("");
            } else if (path.contains(".jar") || path.contains(".JAR")) {
                return new ZipEntry(path);
            }
            return new DirEntry(path);
        } else {
            //如果命令行中没有显式的指定-cp选项,那么默认要找的class就在当前路径下
            File file = new File("");
            try {
                path = file.getCanonicalPath();
                return new DirEntry(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("illegal classpath format,or you should point out the classpath explicitly");
    }
}
