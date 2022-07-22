package jvm.entry;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author jinfan 2022-07-21
 */
public class DirEntry implements Entry {

    private String classDir;

    public DirEntry(String classDir) {
        File file = new File(classDir);
        if (file.exists()) {
            this.classDir = file.getAbsolutePath();
        } else {
            throw new RuntimeException("classPath路径不存在");
        }
    }

    @Override
    public byte[] readClass(String className) {
        File clazzFile = new File(classDir, className);
        if (clazzFile.exists()) {
            try (BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(clazzFile.toPath()), 1024)) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int size = 0;
                while ((size = stream.read(temp)) != -1) {
                    outStream.write(temp, 0, size);
                }
                return outStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
