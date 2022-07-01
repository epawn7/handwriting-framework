package aop;

import java.lang.reflect.Method;
import aop.anno.MethodInvoke;
import lombok.Builder;
import lombok.Data;

/**
 * @author jinfan 2022-06-27
 */
@Data
@Builder
public class MethodInvocation {

    private Method method;

    private Object[] args;

    private Object bean;

    private MethodInvoke invoke;

    public Object process() {
        return invoke.invoke(this);
    }

}
