package ioc;

/**
 * @author jinfan 2022-05-30
 */
public interface Factory {

    <T> T newBean(BeanInstance<T> bean);

    boolean isEarlyBean();

    <T> T newEarlyBean(Class<T> clazz);

}
