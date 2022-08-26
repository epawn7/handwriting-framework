package jvm.jnative.lang;

import jvm.jnative.Registry;
import jvm.rtda.Frame;

public class NNumber {

    static Registry registry = Registry.getInstance();

    static {
        registry.register("java/lang/Float", "floatToRawIntBits", "(F)I", NNumber::floatToRawIntBits);
        registry.register("java/lang/Double", "doubleToRawLongBits", "(D)J", NNumber::doubleToRawLongBits);
        registry.register("java/lang/Double", "longBitsToDouble", "(J)D", NNumber::longBitsToDouble);
    }

    static private void floatToRawIntBits(Frame frame) {
        float val = frame.getLocalVars().getFloat(0);
        frame.getOperandStack().pushInt(Float.floatToRawIntBits(val));
    }

    static private void doubleToRawLongBits(Frame frame) {
        double val = frame.getLocalVars().getDouble(0);
        frame.getOperandStack().pushLong(Double.doubleToRawLongBits(val));
    }

    static private void longBitsToDouble(Frame frame) {
        long val = frame.getLocalVars().getLong(0);
        frame.getOperandStack().pushDouble(Double.longBitsToDouble(val));
    }

}
