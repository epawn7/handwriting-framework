package aop;

import ioc.anno.Autowire;
import ioc.anno.Compontment;

/**
 * @author jinfan 2022-06-28
 */
@Compontment
public class ServiceB {


    @Autowire
    private ServiceAI serviceAI;

    public void print(){
        System.out.println("BBBBBBBB");
    }

    @LogAnn2
    public void print2(){
        System.out.println("222222");
        serviceAI.print2();
    }

}
