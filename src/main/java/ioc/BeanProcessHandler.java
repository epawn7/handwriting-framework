package ioc;

/**
 * @author jinfan 2022-06-24
 */
public interface BeanProcessHandler {

    <T> T beforeCreateBean(BeanInstance<T> beanInstance);

    boolean afterCreateBean(Object bean,Class<?> clazz);
}
