package jvm;

public class TestMain {

//    public static int test() {
//        int sum = 0;
//        for (int i = 0; i < 100; i++) {
//            sum += i;
//        }
//        return sum;
//    }

//    public static void main(String[] args) {
//        System.out.println(test());
//    }


    public static int staticVar;

    public int instanceVar;

    public static void main(String[] args) {
        int x = 32768; // ldc
        TestMain myObj = new TestMain(); // new
        TestMain.staticVar = x; // putstatic
        x = TestMain.staticVar; // getstatic
        myObj.instanceVar = x; // putfield
        x = myObj.instanceVar; // getfield
        Object obj = myObj;
        if (obj instanceof TestMain) { // instanceof
            myObj = (TestMain) obj; // checkcast
            System.out.println(myObj.instanceVar);
        }
    }

    public static int test() {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }

//    public static void test() {
//        OperandStack stack = new OperandStack(10);
//        stack.pushInt(100);
//        stack.pushInt(-100);
//        stack.pushLong(2997934580L);
//        stack.pushLong(-2997934580L);
//        stack.pushFloat(3.1415925f);
//        stack.pushDouble(2.141592678912);
//
//        System.out.println(stack.popDouble());
//        System.out.println(stack.popFloat());
//        System.out.println(stack.popLong());
//        System.out.println(stack.popLong());
//        System.out.println(stack.popInt());
//        System.out.println(stack.popInt());
//    }
}
