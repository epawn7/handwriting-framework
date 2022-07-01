package aop;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author jinfan 2022-06-30
 */
public class ProxyFactory {

    public Object createAopProxy(Class<?> clazz) {


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
//        enhancer.setCallbacks();

        return null;
    }

    public Object getProxy() {
        return null;
    }


}
