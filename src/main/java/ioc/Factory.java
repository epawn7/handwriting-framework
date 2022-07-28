package ioc;

public interface Factory {

    <T> T newBean(BeanInstance<T> bean);

    boolean isEarlyBean();

    <T> T newEarlyBean(Class<T> clazz);

}
