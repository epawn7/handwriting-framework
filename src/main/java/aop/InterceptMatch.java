package aop;

import java.lang.reflect.Method;

public interface InterceptMatch {

    boolean match(Method method);

}
