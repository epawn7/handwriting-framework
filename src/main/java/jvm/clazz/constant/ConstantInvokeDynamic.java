package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-27
 */
public class ConstantInvokeDynamic extends ConstantInfo {

    short bootstrapMethodAttrIndex;

    short nameAndTypeIndex;

    @Override
    public void readBytes(ClassReader reader) {
        this.bootstrapMethodAttrIndex = reader.readU2ToShort();
        this.nameAndTypeIndex = reader.readU2ToShort();
    }

}
