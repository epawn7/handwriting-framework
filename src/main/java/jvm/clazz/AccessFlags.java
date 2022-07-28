package jvm.clazz;

/**
 * 访问标志
 *
 * 这个标示用来识别类或者接口的访问信息，
 * 两字节供 16 位，目前只定义了 8 位，
 * 没有用到的一律用 0 来填充，
 * 以备以后的拓展使用。
 */
public enum AccessFlags {
    /**
     * 是否为 public 类型
     */
    ACC_PUBLIC(0x0001),
    /**
     * 字段是否为 private
     */
    ACC_PRIVATE(0x0002),
    /**
     * 字段是否为 protected
     */
    ACC_PROTECTED(0x0004),
    /**
     * 字段是否为 static
     */
    ACC_STATIC(0x0008),
    /**
     * 是否被声明为 final，只有类可设置
     */
    ACC_FINAL(0x0010),
    /**
     * 是否允许使用 invokespecial 字节码指令的新语义，
     * JDK1.0.2 之后编译出来的这个标志都必须为 1
     */
    ACC_SUPER(0x0020),
    /**
     * 字段是否为 volatile
     */
    ACC_VOLATILE(0x0040),
    /**
     * 字段是否为 transient
     */
    ACC_TRANSIENT(0x0080),
    /**
     * 方法是否为 native
     */
    ACC_NAVIVE(0x0100),
    /**
     * 标示这是一个接口
     */
    ACC_INTERFACE(0x0200),
    /**
     * 是否为 abstract 类型，对于接口或者抽象类来说，此标志为真，其他类值为假
     */
    ACC_ABSTRACT(0x0400),
    /**
     * 方法是否为 strictfp
     */
    ACC_STRICTFP(0x0800),
    /**
     * 标志是否由编译器自动产生
     */
    ACC_SYNTHETIC(0x1000),
    /**
     * 标志这是一个注解
     */
    ACC_ANNOTATION(0x2000),
    /**
     * 标志这是一个枚举
     */
    ACC_ENUM(0x4000),

    ;

    private final int code;

    AccessFlags(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}