package ioc;

import ioc.anno.Autowire;
import ioc.anno.Compontment;

@Compontment
public class ServiceA {
    @Autowire
    private ServiceB serviceB;

    public void print(){
        System.out.println("service a");
        serviceB.print();
    }

}
