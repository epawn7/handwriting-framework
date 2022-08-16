package jvm.clazz;

import jvm.clazz.attribute.AttributeInfo;

/**
 * 字段表
 *
 * field_info {
 * u2 access_flags;		//字段的访问修饰符
 * u2 name_index;		//常量池索引，代表字段的简单名称
 * u2 descriptor_index;	//常量池索引，代表字段描述符
 * u2 attributes_count;	//字段的额外附加属性数量
 * attribute_info attributes[attributes_count];	//字段的额外的附加属性
 * }
 *
 2022-07-26
 */
public class FieldInfo {

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

    public short getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(short accessFlags) {
        this.accessFlags = accessFlags;
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(short nameIndex) {
        this.nameIndex = nameIndex;
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(short descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
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
