package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 *
 */
public class ConstantMethodType extends ConstantInfo {

    short descriptorIndex;

    public ConstantMethodType(byte type) {
        super(type);
    }


    @Override
    public void readBytes(ClassReader reader) {
        this.descriptorIndex = reader.readU2ToShort();
    }

}
