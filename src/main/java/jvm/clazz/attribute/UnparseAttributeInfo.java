package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 2022-07-28
 */
public class UnparseAttributeInfo extends AttributeInfo {

    byte[] bytes;

    public UnparseAttributeInfo(String attributeName) {
        super(attributeName);
    }

    @Override
    public void parseBytes(ClassReader reader) {
        bytes = reader.readBytes(attributeLength);
    }

}
