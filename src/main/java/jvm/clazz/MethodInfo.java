package jvm.clazz;

import jvm.clazz.attribute.AttributeInfo;

/**
 * 方法表,方法和字段描述基本一样
 * field_info {
 * u2 access_flags;		//方法的访问修饰符
 * u2 name_index;		//常量池索引，代表方法的简单名称
 * u2 descriptor_index;	//常量池索引，代表方法描述符
 * u2 attributes_count;	//方法的额外附加属性数量
 * attribute_info attributes[attributes_count];	//方法的额外的附加属性
 * }
 *
 * @author jinfan 2022-07-26
 */
public class MethodInfo {

    /**
     * 访问修饰符
     *
     * @see AccessFlags
     */
    short accessFlags;

    /**
     * 字段名称
     */
    short nameIndex;

    /**
     * 字段描述符号
     */
    short descriptorIndex;

    /**
     * 字段的额外附加属性数量
     */
    short attributesCount;

    /**
     * 字段的额外的附加属性
     */
    AttributeInfo attributes[];

}
