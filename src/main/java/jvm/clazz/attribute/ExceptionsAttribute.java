package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * Exceptions_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 number_of_exceptions;
 * u2 exception_index_table[number_of_exceptions];
 * }
 */
public class ExceptionsAttribute extends AttributeInfo {

    short numberOfExceptions;

    /**
     * 数组中保存的是一个指向常量池中 CONSTANT_Class_info 型的常量的索引，代表该异常的类型。
     */
    short[] exceptionIndexTable;

    public ExceptionsAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    public void parseBytes(ClassReader reader) {
        numberOfExceptions = reader.readU2ToShort();
        if (numberOfExceptions > 0) {
            exceptionIndexTable = new short[numberOfExceptions];
            for (int i = 0; i < numberOfExceptions; i++) {
                exceptionIndexTable[i] = reader.readU2ToShort();
            }
        }
    }

}
