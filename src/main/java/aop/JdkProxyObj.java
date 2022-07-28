package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import aop.MethodInvocation.MethodInvocationBuilder;
import aop.anno.MethodInvoke;
import ioc.IocContainer;
import lombok.Data;

@Data
public class JdkProxyObj implements InvocationHandler, ProxyObj {

    private Object instance;

    private Map<Method, List<Class<?>>> methodMap;

    private transient Map<Method, Method> methodCache;

    public JdkProxyObj() {
        methodCache = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodInvocation.MethodInvocationBuilder invocationBuilder = new MethodInvocationBuilder();
        Method realMethod = getRealMethod(method);
        List<Class<?>> methodInterceptors = methodMap.get(realMethod);
        Iterator<Class<?>> iterator = methodInterceptors.iterator();
        Class<?> aopClazz = iterator.next();
        MethodInterceptor node = (MethodInterceptor) IocContainer.getInstance().getBean(aopClazz);
        MethodInvoke methodInvoke = ((invocation) -> {
            if (iterator.hasNext()) {
                return ((MethodInterceptor) IocContainer.getInstance().getBean(iterator.next())).invoke(invocation);
            } else {
                try {
                    return invocation.getMethod().invoke(invocation.getBean(), invocation.getArgs());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
        MethodInvocation methodInvocation = invocationBuilder.bean(instance).method(method).args(args)
                .invoke(methodInvoke).build();
        return node.invoke(methodInvocation);
    }

    private Method getRealMethod(Method method) {
        if (method.getDeclaringClass().equals(instance.getClass())) {
            return method;
        }
        Method realMethod = methodCache.get(method);
        if (realMethod != null) {
            return realMethod;
        }
        for (Method m : methodMap.keySet()) {
            if (m.getName().equals(method.getName()) && m.getReturnType().equals(method.getReturnType())
                    && equalParamTypes(m.getParameterTypes(), method.getParameterTypes())) {
                methodCache.put(method, m);
                return m;
            }
        }
        return null;
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

    @Override
    public void setOriginObj(Object obj) {
        this.instance = obj;
    }

}
