package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 *
 */
public class ConstantInteger extends ConstantInfo {

    int value;

    public ConstantInteger(byte type) {
        super(type);
    }

    @Override
    public void readBytes(ClassReader reader) {
        value = reader.readU4ToInt();
    }

    public int getValue() {
        return value;
    }

}
