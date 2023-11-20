package jvm.jnative.lang;

import jvm.jnative.Registry;
import jvm.rtda.Frame;

public class NThrowable {

    static Registry registry = Registry.getInstance();

    static {
        registry.register("java/lang/Throwable", "fillInStackTrace", "(I)Ljava/lang/Throwable;",
                NThrowable::fillInStackTrace);
    }

    private static void fillInStackTrace(Frame frame) {

    }

}
