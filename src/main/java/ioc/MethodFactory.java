package ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import util.Log;

/**
 * @author jinfan 2022-05-30
 */
@Data
public class MethodFactory implements Factory {

    BeanFactory beanFactory;

    public MethodFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T> T newBean(BeanInstance<T> instance) {
        MethodBean<T> bean = (MethodBean<T>) instance;
        Log.debug("开始初始化方法" + bean.getMethod().getName());
        List<?> paramList = bean.getParamList().stream().map(clz -> beanFactory.getBean(clz))
                .collect(Collectors.toList());
        try {
            T obj = (T) bean.getMethod().invoke(bean.getClassObj(), paramList.toArray());
            bean.setInstanct(obj);
            return obj;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isEarlyBean() {
        return false;
    }

    @Override
    public <T> T newEarlyBean(Class<T> clazz) {
        throw new RuntimeException("不支持提前初始化");
    }

}
