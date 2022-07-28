package jvm.entry;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class ZipEntry implements Entry {

    private String classDir;

    private String fileName;

    public ZipEntry(String classDir) {
        File file = new File(classDir);
        if (file.exists()) {
            this.classDir = file.getAbsolutePath();
            fileName = classDir.substring(classDir.lastIndexOf(File.separator) + 1, classDir.length() - 4);
        }
    }

    @Override
    public byte[] readClass(String className) {
        try (ZipFile file = new ZipFile(classDir)) {
            java.util.zip.ZipEntry entry = file.getEntry(className);
            if (entry == null) {
                return null;
            }
            try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream(entry))) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int size = 0;
                while ((size = inputStream.read(temp)) != -1) {
                    outStream.write(temp, 0, size);
                }
                return outStream.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
