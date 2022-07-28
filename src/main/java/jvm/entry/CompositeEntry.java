package jvm.entry;

import static jvm.entry.ClassPath.pathListSeparator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeEntry implements Entry{

    private List<Entry> subEntry;

    private String pathList;

    public CompositeEntry(String pathList) {
        this.pathList = pathList;
        subEntry = Arrays.stream(pathList.split(pathListSeparator)).map(path -> {
            if (path.endsWith(".jar")) {
                return new ZipEntry(path);
            } else {
                return new DirEntry(path);
            }
        }).collect(Collectors.toList());
    }

    public CompositeEntry(List<Entry> subEntry) {
        this.subEntry = subEntry;
    }

    @Override
    public byte[] readClass(String className) {
        for (Entry entry : subEntry) {
            byte[] data = entry.readClass(className);
            if (data != null) {
                return data;
            }
        }
        return null;
    }

}
