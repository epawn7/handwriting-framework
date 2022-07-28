package scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface ClassParser {

    boolean matchClass(Class<?> clazz);

    boolean matchMethod(Class<?> clazz, Method method, Parameter[] parameters);

    boolean matchField(Class<?> clazz, Field field);

    boolean scanClassOver(Class<?> clazz);

    default boolean scanAllOver() {
        return true;
    }

}
