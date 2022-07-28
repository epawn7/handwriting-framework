package scanner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassScanner {

    private List<ClassParser> classParserList;

    public ClassScanner() {
        classParserList = new ArrayList<>();
    }

    public void addParser(ClassParser parser) {
        classParserList.add(parser);
    }

    public List<Class<?>> scan(String packageName) {
        String sourcePath = packageName.replace(".", "/");
        List<Class<?>> classList = new ArrayList<>();
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(sourcePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url == null) {
                    continue;
                }
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    scanPackageClass(url.getPath().replaceAll("%20", " "), classList, packageName);
                }
            }

            for (Class<?> clazz : classList) {
                for (ClassParser classParser : classParserList) {
                    classParser.matchClass(clazz);

                    Field[] declaredFields = clazz.getDeclaredFields();
                    for (Field field : declaredFields) {
                        classParser.matchField(clazz, field);
                    }
                    Method[] declaredMethods = clazz.getDeclaredMethods();
                    for (Method method : declaredMethods) {
                        classParser.matchMethod(clazz, method, method.getParameters());
                    }
                    classParser.scanClassOver(clazz);
                }
            }
            for (ClassParser classParser : classParserList) {
                classParser.scanAllOver();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classList;
    }

    public void scanPackageClass(String filePath, List<Class<?>> classList, String packageName) {
        File packageDir = new File(filePath);
        File[] childFiles = packageDir.listFiles(
                f -> f.isDirectory() || (f.isFile() && f.getName().endsWith(".class")));
        if (childFiles == null || childFiles.length == 0) {
            return;
        }
        for (File childFile : childFiles) {
            String childFileName = childFile.getName();
            if (childFile.isDirectory()) {
                scanPackageClass(childFile.getPath(), classList, packageName + "." + childFileName);
            } else {
                try {
                    Class<?> aClass = Class.forName(
                            packageName + "." + childFileName.substring(0, childFileName.lastIndexOf(".class")));
                    classList.add(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
