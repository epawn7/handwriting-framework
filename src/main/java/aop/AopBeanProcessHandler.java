package aop;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ioc.BeanInstance;
import ioc.BeanProcessHandler;
import lombok.Data;

/**
 * @author jinfan 2022-06-24
 */
@Data
public class AopBeanProcessHandler implements BeanProcessHandler {

    Map<Class<?>, JdkProxyObj> proxyObjMap = new HashMap<>();

    private List<InterceptMatch> matchList;

    private ProxyFactory proxyFactory;

    @Override
    public <T> T beforeCreateBean(BeanInstance<T> beanInstance) {
        Method[] declaredMethods = beanInstance.originClass().getDeclaredMethods();
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
        JdkProxyObj jdkProxyObj = new JdkProxyObj();
        jdkProxyObj.setMethodMap(methodMap);
        proxyObjMap.put(beanInstance.originClass(), jdkProxyObj);
        Object proxy = Proxy.newProxyInstance(beanInstance.originClass().getClassLoader(),
                beanInstance.originClass().getInterfaces(), jdkProxyObj);
        return (T) proxy;
    }

    @Override
    public boolean afterCreateBean(Object bean, Class<?> clazz) {
        JdkProxyObj jdkProxyObj = proxyObjMap.get(clazz);
        if (jdkProxyObj == null) {
            return true;
        }
        jdkProxyObj.setInstance(bean);
        return true;
    }

}
