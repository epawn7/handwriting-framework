package aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ioc.BeanInstance;
import ioc.BeanProcessHandler;

/**
 * @author jinfan 2022-06-24
 */
public class AopBeanProcessHandler implements BeanProcessHandler {

    private ProxyFactory proxyFactory;

    public AopBeanProcessHandler(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    @Override
    public <T> T beforeCreateBean(BeanInstance<T> beanInstance) {
        Object proxy = proxyFactory.createAopProxy(beanInstance);
        return (T) proxy;
    }

    @Override
    public boolean afterCreateBean(Object bean, Class<?> clazz) {
        proxyFactory.setOriginBean(clazz,bean);
        return true;
    }

    public void setMatchList(List<InterceptMatch> matchList) {
        proxyFactory.setMatchList(matchList);
    }

}
