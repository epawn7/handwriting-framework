package ioc;

public class AbstractBean<T> implements BeanInstance<T> {

    private T instanct;

    private Class<T> clazz;

    private boolean isConstruct;

    public AbstractBean() {
    }

    public AbstractBean(Class<T> clazz) {
        this.clazz = clazz;
        isConstruct = false;
    }

    @Override
    public Class<T> originClass() {
        return clazz;
    }

    @Override
    public boolean isConstruct() {
        return isConstruct;
    }

    @Override
    public void setConstruct(boolean construct) {
        this.isConstruct = construct;
    }

    public T getInstanct() {
        return instanct;
    }

    @Override
    public void setInstanct(T instanct) {
        this.instanct = instanct;
    }

}
