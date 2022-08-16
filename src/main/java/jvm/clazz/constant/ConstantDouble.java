package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 *
 */
public class ConstantDouble extends ConstantInfo {

    double value;

    public ConstantDouble(byte type) {
        super(type);
    }

    @Override
    public void readBytes(ClassReader reader) {
        long temp = reader.readU8ToLong();
        value = Double.longBitsToDouble(temp);
    }

    public double getValue() {
        return value;
    }

}
