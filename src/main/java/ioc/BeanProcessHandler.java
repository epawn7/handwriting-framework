package ioc;

public interface BeanProcessHandler {

    <T> T beforeCreateBean(BeanInstance<T> beanInstance);

    boolean afterCreateBean(Object bean,Class<?> clazz);
}
