package aop;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import aop.MethodInvocation.MethodInvocationBuilder;
import aop.anno.MethodInvoke;
import ioc.IocContainer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author jinfan 2022-06-30
 */
public class CglibProxyObj implements MethodInterceptor {

    private Map<Method, List<Class<?>>> methodMap;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation.MethodInvocationBuilder invocationBuilder = new MethodInvocationBuilder();
        List<Class<?>> methodInterceptors = methodMap.get(method);
        Iterator<Class<?>> iterator = methodInterceptors.iterator();
        Class<?> aopClazz = iterator.next();
        aop.MethodInterceptor node = (aop.MethodInterceptor) IocContainer.getInstance().getBean(aopClazz);
        MethodInvoke methodInvoke = ((invocation) -> {
            if (iterator.hasNext()) {
                return ((aop.MethodInterceptor) IocContainer.getInstance().getBean(iterator.next())).invoke(invocation);
            } else {
                try {
                    return proxy.invokeSuper(obj, args);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
        MethodInvocation methodInvocation = invocationBuilder.method(method).args(args).invoke(methodInvoke).build();
        return node.invoke(methodInvocation);
    }

}
