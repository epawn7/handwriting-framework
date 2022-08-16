package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 *
 */
public class ConstantFloat extends ConstantInfo {

    float value;

    public ConstantFloat(byte type) {
        super(type);
    }

    @Override
    public void readBytes(ClassReader reader) {
        int temp = reader.readU4ToInt();
        value = Float.intBitsToFloat(temp);
    }

    public float getValue() {
        return value;
    }

}
