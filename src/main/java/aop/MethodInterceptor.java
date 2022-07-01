package aop;

/**
 * @author jinfan 2022-06-27
 */
public interface MethodInterceptor extends InterceptMatch {

    Object invoke(MethodInvocation invocation);
}
