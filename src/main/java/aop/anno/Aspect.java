package aop.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jinfan 2022-05-31
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 被拦截包或类的路径
     */
    String[] path = {};

    /**
     * 排序
     */
    int order = 0;

}
