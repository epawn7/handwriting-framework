package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 普通（非接口）方法符号引用
 * CONSTANT_Fieldref_info {
 * u1 tag;
 * u2 class_index;
 * u2 name_and_type_index;
 * }
 *
 * @author jinfan 2022-07-27
 */
public class ConstantMethodref extends ConstantInfo {

    short classIndex;

    short nameAndTypeIndex;

    @Override
    public void readBytes(ClassReader reader) {
        this.classIndex = reader.readU2ToShort();
        this.nameAndTypeIndex = reader.readU2ToShort();
    }

}
