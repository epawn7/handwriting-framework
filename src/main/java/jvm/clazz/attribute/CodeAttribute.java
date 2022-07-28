package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * Code 是变长属性，只存在于 method_info 结构中
 *
 * Code_attribute {
 * u2 attribute_name_index; //指向 CONSTANT_Utf8_info 类型常量的索引，这个常量值固定为“Code”，代表了该属性的属性名称。
 * u4 attribute_length; //代表该属性的长度，包括从 attribute_name_index 开始到 attributes[]数组结束
 * u2 max_stack;
 * u2 max_locals;
 * u4 code_length;
 * u1 code[code_length];
 * u2 exception_table_length;
 * {   u2 start_pc;
 * u2 end_pc;
 * u2 handler_pc;
 * u2 catch_type;
 * } exception_table[exception_table_length];
 * u2 attributes_count;
 * attribute_info attributes[attributes_count];
 * }
 */
public class CodeAttribute extends AttributeInfo {

    /**
     * 代表操作数栈的深度的最大值。在方法执行的任意时刻，操作数栈都不能超过这个深度。
     */
    short maxStack;

    /**
     * 代表操作数栈的深度的最大值。在方法执行的任意时刻，操作数栈都不能超过这个深度
     *
     * 对于 byte、char、short、int、float、boolean、returnAddress 等长度不超过 32 位的数据，
     * 每个局部变量占用一个 Slot，
     * 而 double 和 long 这种 64 位的数据则需要两个 Slot 来存放。
     */
    short maxLocals;

    /**
     * 指示下面的 code 字节码数组的长度。虽然这是一个 u4 类型，理论上最大值可以达到 2^32-1，但是 Java 虚拟机明确规定一个方法中的指令不能超过 65535 条字节码指令，也就是说它实际是使用了 u2 的长度。
     */
    int codeLength;

    /**
     * 存放的是 Java 源程序编译后生成的 字节码指令
     */
    byte[] code;

    /**
     * 指示下面的异常表数组的长度
     */
    short exceptionTableLength;


    ExceptionTable[] exceptionTable;

    /**
     * 指示下面的属性表数组的长度。
     */
    short attributesCount;

    /**
     * Code 本身就已经是属性了，在这个属性的字段中还包括一些其它的属性…那么就存在这个表中。
     */
    AttributeInfo[] attributes;

    public CodeAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    void parseBytes(ClassReader reader) {
        maxStack = reader.readU2ToShort();
        maxLocals = reader.readU2ToShort();
        codeLength = reader.readU4ToInt();
        if (codeLength > 0) {
            code = reader.readBytes(codeLength);
        }
        exceptionTableLength = reader.readU2ToShort();
        if (exceptionTableLength > 0) {
            exceptionTable = new ExceptionTable[exceptionTableLength];
            for (int i = 0; i < exceptionTableLength; i++) {
                exceptionTable[i] = new ExceptionTable();
                exceptionTable[i].startPc = reader.readU2ToShort();
                exceptionTable[i].endPc = reader.readU2ToShort();
                exceptionTable[i].handlerPc = reader.readU2ToShort();
                exceptionTable[i].catchType = reader.readU2ToShort();
            }
        }
        attributesCount = reader.readU2ToShort();
        if (attributesCount > 0) {
            attributes = new AttributeInfo[attributesCount];
            for (int i = 0; i < attributesCount; i++) {
                short attributeNameIndex = reader.readU2ToShort();
                attributes[i] = reader.createAttribute(attributeNameIndex);
            }
        }
    }

    public static class ExceptionTable {

        short startPc;

        short endPc;

        short handlerPc;

        short catchType;

    }

}
