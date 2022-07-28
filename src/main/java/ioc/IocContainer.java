package ioc;

import java.util.List;
import java.util.Map;
import aop.AopBeanProcessHandler;
import aop.AopClassParser;
import aop.ProxyFactory;
import scanner.ClassScanner;

public class IocContainer {

    private static final IocContainer iocContainer = new IocContainer();

    public static IocContainer getInstance() {
        return iocContainer;
    }

    public static NullBean nullBean = new NullBean();

    private ClassScanner scanner;

    private BeanFactory beanFactory;

    private IocContainer() {
        beanFactory = new BeanFactory();
        scanner = new ClassScanner();
        scanner.addParser(new IocClassParser(beanFactory));
        AopBeanProcessHandler processHandler = new AopBeanProcessHandler(new ProxyFactory());
        scanner.addParser(new AopClassParser(processHandler));
        beanFactory.addBeanProcessHandler(processHandler);
    }

    public <T> T getBean(Class<T> tClass) {
        return beanFactory.getBean(tClass);
    }


    public List<Class<?>> scan(Class clazz) {
        return scanner.scan(clazz.getPackage().getName());
    }

    public void init() {
        Map<Class<?>, BeanInstance<?>> beanInfoMap = beanFactory.getBeanInfoMap();
        for (BeanInstance<?> value : beanInfoMap.values()) {
            beanFactory.getBean(value.originClass());
        }
    }

}
