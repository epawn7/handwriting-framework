package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 *
 */
public class ConstantInvokeDynamic extends ConstantInfo {

    short bootstrapMethodAttrIndex;

    short nameAndTypeIndex;

    public ConstantInvokeDynamic(byte type) {
        super(type);
    }

    @Override
    public void readBytes(ClassReader reader) {
        this.bootstrapMethodAttrIndex = reader.readU2ToShort();
        this.nameAndTypeIndex = reader.readU2ToShort();
    }

}
