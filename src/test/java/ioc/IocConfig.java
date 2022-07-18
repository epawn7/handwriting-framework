package ioc;

import ioc.anno.Bean;
import ioc.anno.Config;

/**
 * @author jinfan 2022-05-31
 */
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
