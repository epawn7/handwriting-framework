package ioc;

import ioc.anno.Autowire;
import ioc.anno.Compontment;

/**
 * @author jinfan 2022-05-30
 */
@Compontment
public class ServiceA {
    @Autowire
    private ServiceB serviceB;

    public void print(){
        System.out.println("service a");
        serviceB.print();
    }

}
