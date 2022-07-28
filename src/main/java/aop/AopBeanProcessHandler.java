package aop;

import java.util.List;
import ioc.BeanInstance;
import ioc.BeanProcessHandler;

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
