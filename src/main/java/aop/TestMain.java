package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jinfan 2022-06-01
 */
public class TestMain {

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        InvocationHandler subjectProxy = new SubjectProxy(subject);
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(subjectProxy.getClass().getClassLoader(), subject.getClass().getInterfaces(), subjectProxy);
        proxyInstance.hello("hello");
    }

    public interface Subject {
        void hello(String param);
    }
    //实现
    public static class SubjectImpl implements Subject {
        @Override
        public void hello(String param) {
            System.out.println(param);
        }
    }

    public static class SubjectProxy implements InvocationHandler {

        private Subject subject;

        public SubjectProxy(Subject subject){
            this.subject = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("-------start----------");
            Object invoke = method.invoke(proxy, args);
            System.out.println("-------end-----------");
            return invoke;
        }
    }
}
