package aop;

import ioc.IocContainer;

/**
 * @author jinfan 2022-06-27
 */
public class AopTest {

    public static void main(String[] args) {
        IocContainer container = IocContainer.getInstance();
        container.scan(AopTest.class);
        container.init();
        ServiceAI a = container.getBean(ServiceAI.class);
        a.print();
        ServiceB serviceB = container.getBean(ServiceB.class);
        serviceB.print2();
    }

}
