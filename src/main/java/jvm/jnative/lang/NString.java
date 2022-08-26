package jvm.jnative.lang;

import jvm.jnative.Registry;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.heap.StringPool;

public class NString {

    static Registry registry = Registry.getInstance();

    static {
        registry.register("java/lang/String", "intern", "()Ljava/lang/String;", NString::intern);
    }

    static void intern(Frame frame) {
        Object thisObj = frame.getLocalVars().getRef(0);
        Object jStrObj = StringPool.internString(thisObj);
        frame.getOperandStack().pushRef(jStrObj);
    }

}
