package ioc;

public interface BeanInstance<T> {

    Class<T> originClass();

    boolean isConstruct();

    void setConstruct(boolean construct);

    void setInstanct(T instanct);



}
