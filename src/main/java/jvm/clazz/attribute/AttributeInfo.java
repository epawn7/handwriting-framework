package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-26
 */
public abstract class AttributeInfo {

    String attributeName;

    int attributeLength;

    public AttributeInfo(String attributeName) {
        this.attributeName = attributeName;
    }

    public void readBytes(ClassReader reader) {
        attributeLength = reader.readU4ToInt();
        if (attributeLength > 0) {
            parseBytes(reader);
        }
    }

    abstract void parseBytes(ClassReader reader);

}
