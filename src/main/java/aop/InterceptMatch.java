package aop;

import java.lang.reflect.Method;

/**
 * @author jinfan 2022-06-27
 */
public interface InterceptMatch {

    boolean match(Method method);

}
