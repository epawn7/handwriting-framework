package aop;

public interface MethodInterceptor extends InterceptMatch {

    Object invoke(MethodInvocation invocation);
}
