package ioc;

/**
 * @author jinfan 2022-06-10
 */
public class ResolvableType {

    public Class<?> rawClazz;

    public ResolvableType(Class<?> clazz) {
        rawClazz = clazz;
    }

    public boolean isMatch(Class<?> compareClazz) {
        return compareClazz.isAssignableFrom(rawClazz);
    }

    public static ResolvableType forClass(Class<?> clazz) {
        return new ResolvableType(clazz);
    }

}
