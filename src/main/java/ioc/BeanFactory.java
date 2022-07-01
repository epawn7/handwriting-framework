package ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author jinfan 2022-06-08
 */
@Data
public class BeanFactory {

    private final Map<Class<?>, Object> beanMap;

    private final Map<Class<?>, Object> earlyBeanMap;

    private final Map<Class<?>, BeanInstance<?>> beanInfoMap;

    private final Map<Class<?>, Factory> beanFactoryMap;

    private final Map<Class<?>, Class<?>> cacheTypeMap;

    private List<BeanProcessHandler> processHandlerList;

    private static final NullBean NULL_BEAN = new NullBean();

    public BeanFactory() {
        this.beanMap = new HashMap<>();
        beanInfoMap = new HashMap<>();
        cacheTypeMap = new HashMap<>();
        beanFactoryMap = new HashMap<>();
        earlyBeanMap = new HashMap<>();
        processHandlerList = new ArrayList<>();
    }

    public <T> T getBean(Class<T> clazz) {
        Class<? extends T> beanType = getBeanType(clazz);
        if (beanType == null) {
            return null;
        }
        if (beanMap.containsKey(beanType)) {
            return (T) beanMap.get(beanType);
        }

        if (earlyBeanMap.containsKey(beanType)) {
            return (T) earlyBeanMap.get(beanType);
        }
        Object o = newBean(beanType);
        return (T) o;
    }

    private <T> Object newBean(Class<T> beanType) {
        //bean不存在,进行初始化
        BeanInstance<T> beanInstance = (BeanInstance<T>) beanInfoMap.get(beanType);
        Factory factory = beanFactoryMap.get(beanType);
        T bean;
        T proxy = null;

        for (BeanProcessHandler beanProcessHandler : processHandlerList) {
            proxy = beanProcessHandler.beforeCreateBean(beanInstance);
            if (proxy != null) {
                break;
            }
        }

        if (factory.isEarlyBean()) {
            //果如bean可以提前初始化
            beanInstance.setConstruct(true);
            bean = factory.newEarlyBean(beanInstance.originClass());
            if (proxy != null) {
                beanInstance.setInstanct(bean);
                earlyBeanMap.put(beanType, proxy);
            } else {
                beanInstance.setInstanct(bean);
                earlyBeanMap.put(beanType, bean);
            }
        } else {
            if (beanInstance.isConstruct()) {
                throw new RuntimeException("存在循环依赖:" + beanInstance.originClass().getName());
            }
        }
        beanInstance.setConstruct(true);
        bean = factory.newBean(beanInstance);

        for (BeanProcessHandler beanProcessHandler : processHandlerList) {
            boolean process = beanProcessHandler.afterCreateBean(bean, beanInstance.originClass());
            if (!process) {
                beanInstance.setConstruct(false);
                earlyBeanMap.remove(beanType);
                bean = null;
            }
        }

        if (proxy != null) {
            beanMap.put(beanType, proxy);
        } else {
            beanMap.put(beanType, bean);
        }

        beanInstance.setConstruct(false);
        earlyBeanMap.remove(beanType);
        return bean;
    }

    private <T> Class<? extends T> getBeanType(Class<T> clazz) {
        if (beanInfoMap.containsKey(clazz)) {
            return clazz;
        }
        if (cacheTypeMap.containsKey(clazz)) {
            return (Class<? extends T>) cacheTypeMap.get(clazz);
        }
        for (Class<?> aClass : beanInfoMap.keySet()) {
            if (clazz.isAssignableFrom(aClass)) {
                cacheTypeMap.put(clazz, aClass);
                return (Class<? extends T>) aClass;
            }
        }
        return null;
    }

    public <T> void setBeanInstance(Class<?> type, BeanInstance<?> instance, Factory factory) {
        beanInfoMap.put(type, instance);
        beanFactoryMap.put(type, factory);
    }

    public void addBeanProcessHandler(BeanProcessHandler handler) {
        processHandlerList.add(handler);
    }

}
