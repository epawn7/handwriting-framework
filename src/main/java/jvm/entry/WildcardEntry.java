package jvm.entry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jinfan 2022-07-22
 */
public class WildcardEntry implements Entry {
    private CompositeEntry compositeEntry;

    public WildcardEntry(String classPath) {
        String dirPath = classPath.substring(0, classPath.length() - 1);
        File dirFile = new File(dirPath);
        List<Entry> entryList = new ArrayList<>();

        if (dirFile.exists() && dirFile.isDirectory()) {
            if (dirFile.listFiles() == null) {
                throw new RuntimeException(classPath + "文件夹下不存在文件");
            }
            for (File subFile : dirFile.listFiles()) {
                if (subFile.isFile()) {
                    if (subFile.getName().endsWith(".jar")) {
                        entryList.add(new ZipEntry(subFile.getAbsolutePath()));
                    } else if (subFile.getName().endsWith(".class")) {
                        entryList.add(new DirEntry(subFile.getAbsolutePath()));
                    }
                }
            }
            if (entryList.isEmpty()) {
                throw new RuntimeException(classPath + "文件夹下不存在文件");
            }
            compositeEntry = new CompositeEntry(entryList);
        } else {
            throw new RuntimeException("带*地址解析错误");
        }
    }

    @Override
    public byte[] readClass(String className) {
        return compositeEntry.readClass(className);
    }

}
