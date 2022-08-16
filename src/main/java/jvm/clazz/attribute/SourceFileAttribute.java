package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 */
public class SourceFileAttribute extends AttributeInfo {

    /**
     * 常量池索引,指向CONSTANT_Utf8_info.实际内容为源文件的文件名称
     */
    short sourcefileIndex;

    public SourceFileAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    public void parseBytes(ClassReader reader) {
        sourcefileIndex = reader.readU2ToShort();
    }

}
