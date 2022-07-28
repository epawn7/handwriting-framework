package jvm.clazz;

import jvm.clazz.attribute.AttributeInfo;
import jvm.clazz.constant.ConstantInfo;

/**
 * ClassFile {
 * u4 magic;	//魔数
 * u2 minor_version;	//次版本号
 * u2 major_version;	//主版本号
 * u2 constant_pool_count;	//常量池大小
 * cp_info constant_pool[constant_pool_count-1]; //常量池
 * u2 access_flags;	//类访问标志,表明class文件定义的是类还是接口，访问级别是public还是private，等
 * u2 this_class;	//
 * u2 super_class;	//
 * u2 interfaces_count;	//本类实现的接口数量
 * u2 interfaces[interfaces_count];	//实现的接口,存放在数组中
 * u2 fields_count;		//本来中含有字段数
 * field_info fields[fields_count];	//数组中存放这各个字段
 * u2 methods_count;		//本类中含有的方法数
 * method_info methods[methods_count];	//数组中存放着各个方法
 * u2 attributes_count;			//本类中含有的属性数量;
 * attribute_info attributes[attributes_count];	//数组中存放着各个属性
 * }
 */
public class ClassFile {

    /**
     * 魔数 . 0xCAFEBABE
     */
    int magic;

    /**
     * 主版本号(M):2 字节
     */
    short majorVersion;

    /**
     * 次版本号(m):2 字节
     */
    short minorVersion;

    /**
     * 常量池大小
     */
    short constantPoolCount;

    /**
     * 常量池
     */
    ConstantInfo[] constantPool;

    /**
     * 访问标志
     *
     * @see jvm.clazz.ConstantTagEnum
     */
    short accessFlag;

    /**
     * 类索引,用于确定这个类的全限定名.
     * 该 u2 类型的索引值指向常量池中一个类型为 CONSTANT_Class_info 的类描述符常量,
     * 再通过 CONSTANT_Class_info 类型的常量中的索引值,
     * 可以找到定义在 CONSTANT_Utf8_info 类型的常量中的全限定名字符串。
     */
    short thisClass;

    /**
     * 父类索引, 用于确定这个类的父类的全限定名。
     * 除了 java.lang.Obect 之外,所有的 Java 类都有父类，
     * 所以除了 java.lang.Obect 的父类索引为 0，其余都不为 0。
     */
    short superClass;

    /**
     * 接口总数,
     * 如果该类没有实现接口，
     * 则计数器为 0，后面的接口索引表不占用任何字节。
     */
    short interfaceCount;

    /**
     * 接口索引集合,存放了接口在常量池中的索引值。
     */
    short[] interfaces;

    /**
     * 字段总数
     */
    short fieldCount;

    /**
     * 字段表集合
     */
    FieldInfo[] fields;

    /**
     * 方法总数
     */
    short methodCount;

    /**
     * 方法表集合
     */
    MethodInfo[] methods;

    /**
     * 属性总数
     */
    short attributesCount;

    /**
     * 属性表集合
     */
    AttributeInfo[] attributes;

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public short getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(short majorVersion) {
        this.majorVersion = majorVersion;
    }

    public short getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(short minorVersion) {
        this.minorVersion = minorVersion;
    }

    public short getConstantPoolCount() {
        return constantPoolCount;
    }

    public void setConstantPoolCount(short constantPoolCount) {
        this.constantPoolCount = constantPoolCount;
    }

    public ConstantInfo[] getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantInfo[] constantPool) {
        this.constantPool = constantPool;
    }

    public short getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(short accessFlag) {
        this.accessFlag = accessFlag;
    }

    public short getThisClass() {
        return thisClass;
    }

    public void setThisClass(short thisClass) {
        this.thisClass = thisClass;
    }

    public short getSuperClass() {
        return superClass;
    }

    public void setSuperClass(short superClass) {
        this.superClass = superClass;
    }

    public short getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(short interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public short[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(short[] interfaces) {
        this.interfaces = interfaces;
    }

    public short getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(short fieldCount) {
        this.fieldCount = fieldCount;
    }

    public FieldInfo[] getFields() {
        return fields;
    }

    public void setFields(FieldInfo[] fields) {
        this.fields = fields;
    }

    public short getMethodCount() {
        return methodCount;
    }

    public void setMethodCount(short methodCount) {
        this.methodCount = methodCount;
    }

    public MethodInfo[] getMethods() {
        return methods;
    }

    public void setMethods(MethodInfo[] methods) {
        this.methods = methods;
    }

    public short getAttributesCount() {
        return attributesCount;
    }

    public void setAttributesCount(short attributesCount) {
        this.attributesCount = attributesCount;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }

}
