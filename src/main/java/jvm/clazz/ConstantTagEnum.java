package jvm.clazz;

import java.util.Arrays;

/**
 * @author jinfan 2022-07-25
 */
public enum ConstantTagEnum {

    /**
     * UTF-8编码的字符
     */
    CONSTANT_utf8_info((byte) 1),

    /**
     * 整形字面量
     */
    CONSTANT_Integer_info((byte) 3),

    /**
     * 浮点型字面量
     */
    CONSTANT_Float_info((byte) 4),

    /**
     * 长整型字面量
     */
    CONSTANT_Long_info((byte) 5),

    /**
     * 双精度浮点型型字面量
     */
    CONSTANT_Double_info((byte) 6),

    /**
     * 类或接口的符号引用
     */
    CONSTANT_Class_info((byte) 7),

    /**
     * 字符串类型字面量
     */
    CONSTANT_String_info((byte) 8),

    /**
     * 字段的符号引用
     */
    CONSTANT_Fieldref_info((byte) 9),

    /**
     * 类中方法的符号引用
     */
    CONSTANT_Methodref_info((byte) 10),

    /**
     * 接口中方法的符号引用
     */
    CONSTANT_InterfaceMethodref_info((byte) 11),

    /**
     * 接口中方法的符号引用
     */
    CONSTANT_NameAndType_info((byte) 12),

    /**
     * 表示方法句柄
     */
    CONSTANT_MethodHandle_info((byte) 15),

    /**
     * 标志一个方法类型
     */
    CONSTANT_MethodType_info((byte) 16),

    /**
     * 表示一个动态方法调用点
     */
    CONSTANT_InvokeDynamic_info((byte) 18),

    ;

    final byte code;


    ConstantTagEnum(byte code) {
        this.code = code;
    }

    public static ConstantTagEnum of(byte code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.code == code).findFirst().orElse(null);
    }

}