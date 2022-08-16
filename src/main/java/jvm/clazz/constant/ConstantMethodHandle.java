package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 2022-07-27
 */
public class ConstantMethodHandle extends ConstantInfo {

    private byte referenceKind;

    private short referenceIndex;

    public ConstantMethodHandle(byte type) {
        super(type);
    }


    @Override
    public void readBytes(ClassReader reader) {
        this.referenceKind = reader.readU1ToByte();
        this.referenceIndex = reader.readU2ToShort();
    }

}
