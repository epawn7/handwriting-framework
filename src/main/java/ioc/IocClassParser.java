package ioc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import ioc.anno.Bean;
import ioc.anno.Compontment;
import ioc.anno.Config;
import scanner.ClassParser;

public class IocClassParser implements ClassParser {

    private BeanFactory beanFactory;

    private HashSet<Class> markedClass;

    private Factory objFactory;

    private Factory methodFactory;

    private Object configObj;

    public IocClassParser(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        objFactory = new ObjFactory(beanFactory);
        methodFactory = new MethodFactory(beanFactory);
        markedClass = new HashSet<>();
    }

    @Override
    public boolean matchClass(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Compontment.class)) {
            markedClass.add(clazz);
            ObjBean<?> bean = new ObjBean<>(clazz);
            beanFactory.setBeanInstance(clazz, bean, objFactory);
        }
        if (clazz.isAnnotationPresent(Config.class)) {
            markedClass.add(clazz);
            try {
                configObj = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean matchMethod(Class<?> clazz, Method method, Parameter[] parameters) {
        if (markedClass.contains(clazz) && method.isAnnotationPresent(Bean.class)) {
            MethodBean<?> beanInfo = new MethodBean<>(method.getReturnType(), method);
            beanInfo.setClassObj(configObj);
            beanFactory.setBeanInstance(method.getReturnType(), beanInfo, methodFactory);
        }
        return false;
    }

    @Override
    public boolean matchField(Class<?> clazz, Field field) {
        return false;
    }

    @Override
    public boolean scanClassOver(Class<?> clazz) {
        return false;
    }

}
