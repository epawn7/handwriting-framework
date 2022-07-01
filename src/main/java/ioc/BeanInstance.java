package ioc;

/**
 * @author jinfan 2022-06-08
 */
public interface BeanInstance<T> {

    Class<T> originClass();

    boolean isConstruct();

    void setConstruct(boolean construct);

    void setInstanct(T instanct);



}
