package jvm.clazz;

/**
 * ClassFile {
 *   u4 magic;	//魔数
 *   u2 minor_version;	//次版本号
 *   u2 major_version;	//主版本号
 *   u2 constant_pool_count;	//常量池大小
 *   cp_info constant_pool[constant_pool_count-1]; //常量池
 *   u2 access_flags;	//类访问标志,表明class文件定义的是类还是接口，访问级别是public还是private，等
 *   u2 this_class;	//
 *   u2 super_class;	//
 *   u2 interfaces_count;	//本类实现的接口数量
 *   u2 interfaces[interfaces_count];	//实现的接口,存放在数组中
 *   u2 fields_count;		//本来中含有字段数
 *   field_info fields[fields_count];	//数组中存放这各个字段
 *   u2 methods_count;		//本类中含有的方法数
 *   method_info methods[methods_count];	//数组中存放着各个方法
 *   u2 attributes_count;			//本类中含有的属性数量;
 *   attribute_info attributes[attributes_count];	//数组中存放着各个属性
 * }
 *
 */
public class ClassFile {

    private int magic;

    private int majorVersion;

    private int minorVersion;

    private int constantPoolCount;

    private int[] constantPool;

    private int accessFlag;

    private int thisClass;

    private int superClass;

    private int interfaceCount;

    private int[] interfaces;

    private int fieldCount;

    private int[] fields;

    private int methodCount;

    private int[] methods;

    private int attributesCount;

    private int[] attributes;


}
