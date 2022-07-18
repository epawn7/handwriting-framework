package aop;

import ioc.anno.Autowire;
import ioc.anno.Compontment;

/**
 * @author jinfan 2022-06-28
 */
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

}
