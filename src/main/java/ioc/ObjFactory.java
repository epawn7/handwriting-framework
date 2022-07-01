package ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import lombok.Data;
import util.Log;

/**
 * @author jinfan 2022-05-27
 */
@Data
public class ObjFactory implements Factory {

    BeanFactory beanFactory;

    public ObjFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    @Override
    public <T> T newBean(BeanInstance<T> instance) {
        ObjBean<T> bean = (ObjBean<T>) instance;
        try {
            T obj = bean.getInstanct();
            Map<Class<?>, Field> injectFieldMap = bean.getInjectFieldMap();
            Log.debug(bean.originClass().getName() + "注入属性");
            injectFieldMap.forEach((clz, field) -> {
                field.setAccessible(true);
                try {
                    Object o = beanFactory.getBean(clz);
                    if (o != null) {
                        field.set(obj, o);
                        Log.debug("注入属性成功" + bean.originClass().getName() + "." + field.getName());
                    } else {
                        Log.debug("注入属性失败" + bean.originClass().getName() + "." + field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.debug("结束初始化类" + bean.originClass().getName());
        }
        return null;
    }

    @Override
    public boolean isEarlyBean() {
        return true;
    }

    @Override
    public <T> T newEarlyBean(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T obj = constructor.newInstance();
            return obj;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
