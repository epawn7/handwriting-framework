package util;

import java.lang.reflect.Method;

/**
 * @author jinfan 2022-07-06
 */
public class MethodUtil {

    public static boolean compareMethod(Method m1, Method m2) {
        if (m1 == null || m2 == null) {
            return false;
        }
        if (m1 == m2 || m1.equals(m2)) {
            return true;
        }
        if (!m1.getName().equals(m2.getName())) {
            return false;
        }
        if (!m1.getReturnType().equals(m2.getReturnType())) {
            return false;
        }
        Class<?>[] m1ParameterTypes = m1.getParameterTypes();
        Class<?>[] m2ParameterTypes = m2.getParameterTypes();
        if (m1ParameterTypes.length != m2ParameterTypes.length) {
            return false;
        }
        for (int i = 0; i < m1ParameterTypes.length; i++) {
            if (!m1ParameterTypes[i].equals(m2ParameterTypes[i])) {
                return false;
            }
        }
        return true;
    }

}
