package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 普通（非接口）方法符号引用
 * CONSTANT_Fieldref_info {
 * u1 tag;
 * u2 class_index;
 * u2 name_and_type_index;
 * }
 */
public class ConstantMethodref extends ConstantInfo {

    short classIndex;

    short nameAndTypeIndex;

    ConstantInfo[] constantInfos;

    public ConstantMethodref(byte type, ConstantInfo[] constantInfos) {
        super(type);
        this.constantInfos = constantInfos;
    }

    @Override
    public void readBytes(ClassReader reader) {
        this.classIndex = reader.readU2ToShort();
        this.nameAndTypeIndex = reader.readU2ToShort();
    }

    public String getClassName() {
        ConstantClass constantClass = (ConstantClass) constantInfos[classIndex];
        return constantClass.getClassName();
    }

    public String getDescriptor() {
        ConstantNameAndType nameAndType = (ConstantNameAndType) constantInfos[nameAndTypeIndex];
        return nameAndType.getDescriptor();
    }

    public String getName() {
        ConstantNameAndType nameAndType = (ConstantNameAndType) constantInfos[nameAndTypeIndex];
        return nameAndType.getName();
    }

}
