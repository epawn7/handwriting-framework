package aop;

import java.lang.reflect.Method;
import aop.anno.Aspect;
import ioc.anno.Compontment;

@Aspect
@Compontment
public class Test1MethodInterceptor implements MethodInterceptor {

    @Override
    public boolean match(Method method) {
        return method.isAnnotationPresent(LogAnn1.class);
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        System.out.println("----start hello aop1----");
        Object invoke = invocation.process();
        System.out.println("----end hello aop1----");
        return invoke;
    }

}
