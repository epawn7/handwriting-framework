package ioc;

import ioc.anno.Bean;
import ioc.anno.Config;

@Config
public class IocConfig {

    @Bean
    public ServiecC serviecC() {
        return new ServiecC();
    }

    @Bean
    public ServiceD serviceD(ServiecC serviecC) {
        return new ServiceD(serviecC);
    }

}
