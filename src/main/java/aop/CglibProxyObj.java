package aop;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import aop.MethodInvocation.MethodInvocationBuilder;
import aop.anno.MethodInvoke;
import ioc.IocContainer;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyObj implements MethodInterceptor, CallbackFilter, ProxyObj {

    private Map<Method, List<Class<?>>> methodMap;

    private Object instance;

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
                    return method.invoke(instance, args);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
        MethodInvocation methodInvocation = invocationBuilder.method(method).args(args).invoke(methodInvoke).build();
        return node.invoke(methodInvocation);
    }


    @Override
    public int accept(Method method) {
        for (Method m : methodMap.keySet()) {
            if (m.getName().equals(method.getName()) && m.getReturnType().equals(method.getReturnType())
                    && equalParamTypes(m.getParameterTypes(), method.getParameterTypes())) {
                return 1;
            }
        }
        return 0;
    }

    private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        /* Avoid unnecessary cloning */
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void setMethodMap(Map<Method, List<Class<?>>> methodMap) {
        this.methodMap = methodMap;
    }

    @Override
    public void setOriginObj(Object obj) {
        this.instance = obj;
    }

}
