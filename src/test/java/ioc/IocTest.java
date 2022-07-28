package ioc;

public class IocTest {

    public static void main(String[] args) {
        IocContainer container = IocContainer.getInstance();
        container.scan(IocTest.class);
        container.init();
        ServiceA a = container.getBean(ServiceA.class);
        a.print();
        ServiceB b = container.getBean(ServiceB.class);
        b.print();
        b.printb();
    }
}
