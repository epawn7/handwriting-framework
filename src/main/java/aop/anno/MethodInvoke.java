package aop.anno;

import aop.MethodInvocation;

@FunctionalInterface
public interface MethodInvoke {

    Object invoke(MethodInvocation invocation);

}
