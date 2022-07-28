package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * 最简单的属性，仅起标记作用，不包含任何数据。
 * Synthetic 属性用来标记源文件中不存在、由编译器生成的类成员，
 * 引入 Synthetic 属性主要是为了支持嵌套类和嵌套接口。
 *
 * 主要为桥接方法.在使用泛型后,编译器会生成桥接代码
 *
 * @author jinfan 2022-07-26
 */
public class SyntheticAttribute extends AttributeInfo {


    public SyntheticAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    public void parseBytes(ClassReader reader) {
        //由于不包含任何数据，
        // 所以 attribute_length 的值必须是 0。
        // 自然也就没有了后面的 info 数组字段了。
    }

}
