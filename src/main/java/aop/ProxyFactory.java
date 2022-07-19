package aop;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ioc.BeanInstance;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import util.MethodUtil;

/**
 * @author jinfan 2022-06-30
 */
public class ProxyFactory {

    Map<Class<?>, ProxyObj> proxyObjMap = new HashMap<>();

    private List<InterceptMatch> matchList;

    public <T> Object createAopProxy(BeanInstance<T> beanInstance) {
        Class<T> clazz = beanInstance.originClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Map<Method, List<Class<?>>> methodMap = new HashMap<>();
        for (Method declaredMethod : declaredMethods) {
            List<Class<?>> list = new ArrayList<>();
            for (InterceptMatch interceptMatch : matchList) {
                if (interceptMatch.match(declaredMethod)) {
                    list.add(interceptMatch.getClass());
                }
            }
            if (!list.isEmpty()) {
                methodMap.put(declaredMethod, list);
            }
        }
        if (methodMap.isEmpty()) {
            return null;
        }

        //判断使用jdk动态代理还cglib代理
        if (clazz.isInterface()) {
            return createJdkProxy(methodMap, beanInstance);
        }
        if (clazz.getInterfaces().length == 0) {
            return createCglibProxy(methodMap, beanInstance);
        }
        List<Method> methodList = collectInterfaceMethod(clazz);
        for (Method method : methodMap.keySet()) {
            for (Method interfaceMethod : methodList) {
                if (!MethodUtil.compareMethod(method, interfaceMethod)) {
                    return createCglibProxy(methodMap, beanInstance);
                }
            }
        }
        return createJdkProxy(methodMap, beanInstance);
    }

    private List<Method> collectInterfaceMethod(Class<?> clazz) {
        List<Method> methodList = new ArrayList<>();
        if (clazz.isInterface()) {
            methodList.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        } else {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                methodList.addAll(collectInterfaceMethod(anInterface));
            }
        }
        return methodList;
    }

    public Object createJdkProxy(Map<Method, List<Class<?>>> methodMap, BeanInstance beanInstance) {
        JdkProxyObj jdkProxyObj = new aop.JdkProxyObj();
        jdkProxyObj.setMethodMap(methodMap);
        proxyObjMap.put(beanInstance.originClass(), jdkProxyObj);
        Object proxy = Proxy.newProxyInstance(beanInstance.originClass().getClassLoader(),
                beanInstance.originClass().getInterfaces(), jdkProxyObj);
        return proxy;
    }

    public Object createCglibProxy(Map<Method, List<Class<?>>> methodMap, BeanInstance beanInstance) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanInstance.originClass());
        CglibProxyObj proxyObj = new CglibProxyObj();
        proxyObj.setMethodMap(methodMap);
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, proxyObj});
        enhancer.setCallbackFilter(proxyObj);
        proxyObjMap.put(beanInstance.originClass(), proxyObj);
        return enhancer.create();
    }

    public void setMatchList(List<InterceptMatch> matchList) {
        this.matchList = matchList;
    }

    public void setOriginBean(Class<?> clazz, Object bean) {
        ProxyObj proxyObj = proxyObjMap.get(clazz);
        if (proxyObj != null) {
            proxyObj.setOriginObj(bean);
        }
    }

}
