package aop.anno;

import aop.MethodInvocation;

/**
 * @author jinfan 2022-06-01
 */
@FunctionalInterface
public interface MethodInvoke {

    Object invoke(MethodInvocation invocation);

}
