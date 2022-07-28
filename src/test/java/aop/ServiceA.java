package aop;

import ioc.anno.Autowire;
import ioc.anno.Compontment;

@Compontment
public class ServiceA implements ServiceAI {

    @Autowire
    private ServiceB serviceB;

    @Override
    @LogAnn1
    @LogAnn2
    public void print() {
        serviceB.print();
    }

    @LogAnn1
    @Override
    public void print2() {
        System.out.println("service A");
    }

}
