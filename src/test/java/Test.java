import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import ioc.Factory;
import ioc.MethodFactory;

/**
 * @author jinfan 2022-06-08
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(MethodFactory.class.isAssignableFrom(Factory.class));
        System.out.println(AbstractList.class.isAssignableFrom(ArrayList.class));

        System.out.println(Factory.class.isPrimitive());
        System.out.println(Short.TYPE.isPrimitive());
        Class<?>[] interfaces = Crr.class.getInterfaces();
        List<Arr> list= new ArrayList<>();

        System.out.println(interfaces);

    }


    public interface Arr {

    }

    public interface Brr extends Arr{

    }

    public static class Crr implements Brr{

    }
}
