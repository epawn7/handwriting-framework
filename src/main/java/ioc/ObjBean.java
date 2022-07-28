package ioc;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import ioc.anno.Autowire;
import lombok.Data;

@Data
public class ObjBean<T> extends AbstractBean<T> {

    private Map<Class<?>, Field> injectFieldMap;

    public ObjBean(Class<T> clazz) {
        super(clazz);
        init();
    }

    private void init() {
        Field[] fields = super.originClass().getDeclaredFields();
        injectFieldMap = Arrays.stream(fields).filter(f -> f.isAnnotationPresent(Autowire.class))
                .collect(Collectors.toMap(Field::getType, f -> f));
    }

}
