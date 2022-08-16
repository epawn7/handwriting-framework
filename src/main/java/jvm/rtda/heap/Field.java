package jvm.rtda.heap;

import jvm.clazz.FieldInfo;
import jvm.clazz.attribute.AttributeInfo;
import jvm.clazz.attribute.ConstantValueAttribute;
import jvm.clazz.constant.ConstantInfo;
import jvm.clazz.constant.ConstantUtf8;

/**
 * 字段
 */
public class Field extends ClassMember {

    int slotId;

    int constantvalueIndex;

    ;


    public static Field[] newFields(Clazz clazz, FieldInfo[] fieldInfos, ConstantInfo[] constantPool) {
        if (fieldInfos == null) {
            return new Field[0];
        }
        Field[] fields = new Field[fieldInfos.length];
        for (int i = 0; i < fieldInfos.length; i++) {
            Field field = new Field();
            field.accessFlag = fieldInfos[i].getAccessFlags();
            field.clazz = clazz;
            ConstantUtf8 nameValue = (ConstantUtf8) constantPool[fieldInfos[i].getNameIndex()];
            field.name = nameValue.getValue();
            ConstantUtf8 descriptorValue = (ConstantUtf8) constantPool[fieldInfos[i].getDescriptorIndex()];
            field.descriptor = descriptorValue.getValue();
            if (fieldInfos[i].getAttributes() != null) {
                for (AttributeInfo attribute : fieldInfos[i].getAttributes()) {
                    if (attribute instanceof ConstantValueAttribute) {
                        ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute) attribute;
                        field.constantvalueIndex = constantValueAttribute.getConstantvalueIndex() & 0xFFFF;
                    }
                }
            }
            fields[i] = field;
        }

        return fields;
    }

    public boolean isLongOrDouble() {
        return descriptor.equals("J") || descriptor.equals("D");
    }

    public int getSlotId() {
        return slotId;
    }

}
