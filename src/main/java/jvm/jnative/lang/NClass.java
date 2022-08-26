package jvm.jnative.lang;

import jvm.jnative.Registry;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.StringPool;

/**
 * 实现class的native方法
 */
public class NClass {

    static Registry registry = Registry.getInstance();

    static {
        registry.register("java/lang/Object", "getClass", "()Ljava/lang/Class;", NClass::getClass);

        registry.register("java/lang/Class", "getName0", "()Ljava/lang/String;", NClass::getName0);

        registry.register("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z",
                NClass::desiredAssertionStatus0);

        registry.register("java/lang/Class", "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;",
                NClass::getPrimitiveClass);

    }

    public static void getClass(Frame frame) {
        Object thisObj = frame.getLocalVars().getRef(0);
        Object jclass = thisObj.getClazz().getjClass();
        frame.getOperandStack().pushRef(jclass);
    }

    public static void getPrimitiveClass(Frame frame) {
        Object nameObj = frame.getLocalVars().getRef(0);
        String name = StringPool.getString(nameObj);
        Object jclazz = frame.getMethod().getClazz().getClassLoader().loadClass(name).getjClass();
        frame.getOperandStack().pushRef(jclazz);
    }

    public static void getName0(Frame frame) {
        Object thisObj = frame.getLocalVars().getRef(0);
        Clazz clazz = (Clazz) thisObj.getExtra();
        String name = clazz.getJavaName();
        Object strObj = StringPool.newJString(frame.getMethod().getClazz().getClassLoader(), name);
        frame.getOperandStack().pushRef(strObj);
    }

    public static void desiredAssertionStatus0(Frame frame) {
        frame.getOperandStack().pushBool(false);
    }

}
