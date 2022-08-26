package jvm.rtda.heap;


import java.util.HashMap;
import jvm.rtda.Object;

/**
 * 字符串池
 */
public class StringPool {

    private static final HashMap<String, Object> internedStrings = new HashMap<>();

    public static Object newJString(ClassLoader classLoader, String str) {
        Object jString = internedStrings.get(str);
        if (jString != null) {
            return jString;
        }
        //创建char[]对象
        Clazz charClass = classLoader.loadClass("[C");
        Object charArry = new Object(charClass, str.toCharArray());
        //创建String对象
        Clazz strClass = classLoader.loadClass("java/lang/String");
        Object strObj = new Object(strClass);
        strObj.setRefVar("value", "[C", charArry);
        internedStrings.put(str, strObj);
        return strObj;
    }

    /**
     * 获取String对象中的字符串
     */
    public static String getString(Object strObj) {
        Object chars = strObj.getRefVar("value", "[C");
        return String.valueOf(chars.getChars());
    }

    public static Object internString(Object jstr) {
        String str = getString(jstr);
        if (internedStrings.containsKey(str)) {
            return internedStrings.get(str);
        } else {
            internedStrings.put(str, jstr);
            return jstr;
        }
    }

}
