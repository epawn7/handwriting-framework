package ioc;

import ioc.anno.Autowire;
import ioc.anno.Compontment;

/**
 2022-05-30
 */
@Compontment
public class ServiceB {

    @Autowire
    private ServiceA serviceA;

    @Autowire
    private ServiceB serviceB;

    @Autowire
    private ServiecC serviecC;

    @Autowire
    private ServiceD serviceD;

    public void print() {
        System.out.println("service b");
    }

    public void printb(){
        serviceB.print();
        serviceA.print();
        serviecC.print();
        serviceD.printb();;
    }

}
