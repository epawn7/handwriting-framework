package ioc;

/**
 * @author jinfan 2022-05-31
 */
public class ServiceD {

    private ServiecC serviecC;

    public ServiceD(ServiecC serviecC) {
        this.serviecC = serviecC;
    }

    public void printb() {
        System.out.println("service d");
        serviecC.print();
    }

}
