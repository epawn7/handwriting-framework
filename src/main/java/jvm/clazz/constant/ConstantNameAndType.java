package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 给出字段或方法的名称和描述符。
 * CONSTANT_Class_info 和 CONSTANT_NameAndType_info 加在一起可以唯一确定一个字段或者方法。
 * name_index 和 descriptor_index 都是常量池索引，指向 CONSTANT_Utf8_info 常量。
 * 字段和方法名就是代码中出现的（或者编译器生成的）字段或方法的名字。
 *
 * @author jinfan 2022-07-25
 */
public class ConstantNameAndType extends ConstantInfo {

    /**
     * 字段名或方法名索引
     */
    short nameIndex;

    /**
     *
     */
    short descriptorIndex;

    @Override
    public void readBytes(ClassReader reader) {
        nameIndex = reader.readU2ToShort();
        descriptorIndex = reader.readU2ToShort();
    }

}