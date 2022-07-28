package aop;

import java.lang.reflect.Method;
import aop.anno.Aspect;
import ioc.anno.Compontment;

@Aspect
@Compontment
public class Test2MethodInterceptor implements MethodInterceptor {

    @Override
    public boolean match(Method method) {
        return method.isAnnotationPresent(LogAnn2.class);
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        System.out.println("----start hello aop2----");
        Object invoke = invocation.process();
        System.out.println("----end hello aop2----");
        return invoke;
    }

}
