package ioc;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

@Data
public class MethodBean<T> implements BeanInstance<T> {

    private T instanct;

    private Class<T> clazz;

    private List<Class<?>> paramList;

    private boolean isConstruct;

    private Method method;

    private Object classObj;

    public MethodBean() {
    }

    public MethodBean(Class<T> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
        paramList = Arrays.asList(method.getParameterTypes());
    }

    public MethodBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<T> originClass() {
        return clazz;
    }

}
