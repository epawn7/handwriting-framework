package aop.cglib;

import java.lang.reflect.Method;
import org.objectweb.asm.Type;
import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.transform.ClassEmitterTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;

/**
 * @author jinfan 2022-06-29
 */
public class TestMain {

    public static void main(String[] args) {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(Student.class);
//        enhancer.setCallback(new Interceptor());
//        Student student = (Student) enhancer.create();
//        student.print();


        Enhancer enhancer1 = new Enhancer();
        enhancer1.setSuperclass(Student.class);
        Callback noopCb = NoOp.INSTANCE;
        Callback[] cbarr = {noopCb, new Interceptor()};
        enhancer1.setCallbacks(cbarr);
        enhancer1.setCallbackFilter(new MethodCallbackFileter());
        enhancer1.setStrategy(new MyClassStrategy());
        enhancer1.setInterfaces(new Class[]{MyInterface.class});
        Student student1 = (Student) enhancer1.create();
        student1.print();
        System.out.println(student1.num(20));
    }

    public static class MyClassStrategy extends DefaultGeneratorStrategy {

        @Override
        protected ClassGenerator transform(ClassGenerator cg) throws Exception {
            ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
                @Override
                public void end_class() {
                    declare_field(Constants.ACC_PUBLIC, "tempNUm", Type.getType(Integer.class), 10);
                    super.end_class();
                }
            };
            return new TransformingClassGenerator(cg,transformer);
        }

    }

    public static interface MyInterface {

        void setNum(Integer num);

    }


    public static class Interceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("cglib调用前");
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("cglib调用后");
            return result;
        }

    }

    public static class MethodCallbackHelper extends CallbackHelper {

        public MethodCallbackHelper(Class superclass, Class[] interfaces) {
            super(superclass, interfaces);
        }

        @Override
        protected Object getCallback(Method method) {
            System.out.println(method.getDeclaringClass().getName());
            System.out.println(method.toString());
            return null;
        }

    }

    public static class MethodCallbackFileter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            if (method.getName().equals("print")) {
                System.out.println("filter-print");
                return 0;
            }
            if (method.getName().equals("num")) {
                System.out.println("filter-num");
                return 1;
            }
            return 0;
        }

    }

}
