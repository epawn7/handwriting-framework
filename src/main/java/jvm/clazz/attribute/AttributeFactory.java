package jvm.clazz.attribute;

import ioc.anno.Compontment;

/**
 * @author jinfan 2022-07-28
 */
@Compontment
public class AttributeFactory {

    public AttributeInfo create(String attributeName) {
        switch (attributeName) {
            case "ConstantValue":
                return new ConstantValueAttribute(attributeName);
            case "Code":
                return new CodeAttribute(attributeName);
            case "Exceptions":
                return new ExceptionsAttribute(attributeName);
            case "SourceFile":
                return new SourceFileAttribute(attributeName);
            case "Synthetic":
                return new SyntheticAttribute(attributeName);
            case "Deprecated":
                return new DeprecatedAttribute(attributeName);
            case "LineNumberTable":
                return new LineNumberTableAttribute(attributeName);
            case "LocalVariableTable":
                return new LocalVariableTableAttribute(attributeName);
            default:
                return new UnparseAttributeInfo(attributeName);
        }
    }

}
