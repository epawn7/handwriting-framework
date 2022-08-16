package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * 最简单的属性，仅起标记作用，不包含任何数据。
 * Deprecated 属性用于指出类、接口、字段或方法已经不建议使用
 *
 2022-07-26
 */
public class DeprecatedAttribute extends AttributeInfo {

    public DeprecatedAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    public void parseBytes(ClassReader reader) {
        //由于不包含任何数据，所以 attribute_length 的值必须是 0。
        // 自然也就没有了后面的 info 数组字段了。
    }

}
