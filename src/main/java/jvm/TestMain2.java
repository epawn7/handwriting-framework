package jvm;

public class TestMain2 implements Runnable {

    public static void main(String[] args) {
        new TestMain2().test();
    }

    public void test() {
        TestMain2.staticMethod(); // invokestatic
        TestMain2 demo = new TestMain2(); // invokespecial
        demo.instanceMethod(); // invokespecial
        super.equals(null); // invokespecial
        this.run(); // invokevirtual
        ((Runnable) demo).run(); // invokeinterface
    }

    public static void staticMethod() {
    }

    private void instanceMethod() {
    }

    @Override
    public void run() {
    }

}
