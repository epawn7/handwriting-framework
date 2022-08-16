package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 *
 */
public class ConstantLong extends ConstantInfo {

    long value;

    public ConstantLong(byte type) {
        super(type);
    }

    @Override
    public void readBytes(ClassReader reader) {
        value = reader.readU8ToLong();
    }

    public long getValue() {
        return value;
    }

}
