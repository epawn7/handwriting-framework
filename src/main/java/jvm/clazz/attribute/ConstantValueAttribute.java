package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * ConstantValue 是定长属性，只会出现在 field_info 结构中，用于表示常量表达式的值。
 * 只有被 static 修饰的变量（类变量）才可以使用这项属性。
 *
 2022-07-26
 */
public class ConstantValueAttribute extends AttributeInfo {

    /**
     * 指向常量池中一个字面量类别
     * （CONSTANT_Integer、CONSTANT_Float、CONSTANT_Long、CONSTANT_Double、CONSTANT_Utf8 五种中的一种）的索引，
     * 该常量中保存着变量的值。
     */
    short constantvalueIndex;

    public ConstantValueAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    void parseBytes(ClassReader reader) {
        constantvalueIndex = reader.readU2ToShort();
    }

    public short getConstantvalueIndex() {
        return constantvalueIndex;
    }

}
