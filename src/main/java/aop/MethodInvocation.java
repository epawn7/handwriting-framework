package aop;

import java.lang.reflect.Method;
import aop.anno.MethodInvoke;
import lombok.Builder;
import lombok.Data;

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
