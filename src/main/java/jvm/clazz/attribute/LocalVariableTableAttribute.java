package jvm.clazz.attribute;

/**
 * 存放方法的局部变量信息,属于调试信息，不是运行时必需的。
 * 在使用javac编译器编译Java程序时，
 * 默认会在class文件中生成这些信息。
 *
 * 结构和 LineNumberTableAttribute 类似,暂不实现
 */
public class LocalVariableTableAttribute extends UnparseAttributeInfo {

    public LocalVariableTableAttribute(String attributeName) {
        super(attributeName);
    }

}
