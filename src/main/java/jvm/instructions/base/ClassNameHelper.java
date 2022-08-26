package jvm.instructions.base;

import java.util.HashMap;
import java.util.Map.Entry;

public class ClassNameHelper {

    public static HashMap<String, String> primitiveTypes;

    static {
        primitiveTypes = new HashMap<String, String>();
        primitiveTypes.put("void", "V");
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
    }

    public static String getArrayClassName(String className) {
        if (className.startsWith("[")) {
            return className;
        }
        return "[" + toDescriptor(className);
    }

    public static String toDescriptor(String className) {
        if (className.startsWith("[")) {
            return className;
        }
        if (primitiveTypes.containsKey(className)) {
            return primitiveTypes.get(className);
        }

        // object
        return "L" + className + ";";
    }

    // [[XXX -> [XXX
    // [LXXX; -> XXX
    // [I -> int
    public static String getComponentClassName(String name) {
        if (name.startsWith("[")) {
            String className = name.substring(1);
            if (className.startsWith("[")) {
                return className;
            }
            if (className.startsWith("L")) {
                return className.substring(1, className.length() - 1);
            }
            for (Entry<String, String> entry : primitiveTypes.entrySet()) {
                if (entry.getValue().equals(className)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

}
