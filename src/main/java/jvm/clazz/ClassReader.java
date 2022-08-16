package jvm.clazz;

import java.util.Arrays;
import ioc.anno.Autowire;
import ioc.anno.Compontment;
import jvm.clazz.attribute.AttributeFactory;
import jvm.clazz.attribute.AttributeInfo;
import jvm.clazz.constant.ConstantDouble;
import jvm.clazz.constant.ConstantFactory;
import jvm.clazz.constant.ConstantInfo;
import jvm.clazz.constant.ConstantLong;
import jvm.clazz.constant.ConstantUtf8;
import util.ByteUtil;

/**
 2022-07-22
 */
@Compontment
public class ClassReader {

    private int index = 0;

    private byte[] classBytes;

    private ClassFile classFile;

    @Autowire
    private ConstantFactory constantFactory;

    @Autowire
    private AttributeFactory attributeFactory;

    public ClassFile read(byte[] classBytes) {
        index = 0;
        this.classBytes = classBytes;
        classFile = new ClassFile();
        byte[] bytes = readBytes(4);
        String magic = ByteUtil.bytesToHexString(bytes, bytes.length);
        if (!"CAFEBABE".equals(magic)) {
            throw new RuntimeException("非class文件");
        }
        classFile.minorVersion = readU2ToShort();
        classFile.majorVersion = readU2ToShort();
        classFile.constantPoolCount = readU2ToShort();
        classFile.constantPool = new ConstantInfo[classFile.constantPoolCount];
        for (int i = 1; i < classFile.constantPoolCount; i++) {
            byte tag = readU1ToByte();
            classFile.constantPool[i] = constantFactory.create(tag, classFile.constantPool);
            classFile.constantPool[i].readBytes(this);
            if ((classFile.constantPool[i] instanceof ConstantLong)
                    || (classFile.constantPool[i] instanceof ConstantDouble)) {
                i++;
            }
        }
        classFile.accessFlag = readU2ToShort();
        classFile.thisClass = readU2ToShort();
        classFile.superClass = readU2ToShort();
        classFile.interfaceCount = readU2ToShort();
        if (classFile.interfaceCount > 0) {
            classFile.interfaces = new short[classFile.interfaceCount];
            for (int i = 0; i < classFile.interfaceCount; i++) {
                classFile.interfaces[i] = readU2ToShort();
            }
        }
        classFile.fieldCount = readU2ToShort();
        if (classFile.fieldCount > 0) {
            classFile.fields = new FieldInfo[classFile.fieldCount];
            for (int i = 0; i < classFile.fieldCount; i++) {
                classFile.fields[i] = new FieldInfo();
                classFile.fields[i].accessFlags = readU2ToShort();
                classFile.fields[i].nameIndex = readU2ToShort();
                classFile.fields[i].descriptorIndex = readU2ToShort();
                classFile.fields[i].attributesCount = readU2ToShort();
                if (classFile.fields[i].attributesCount > 0) {
                    classFile.fields[i].attributes = new AttributeInfo[classFile.fields[i].attributesCount];
                    for (int j = 0; j < classFile.fields[i].attributesCount; j++) {
                        short attributeNameIndex = readU2ToShort();
                        classFile.fields[i].attributes[j] = createAttribute(attributeNameIndex);
                        ;
                    }
                }
            }
        }
        classFile.methodCount = readU2ToShort();
        if (classFile.methodCount > 0) {
            classFile.methods = new MethodInfo[classFile.methodCount];
            for (int i = 0; i < classFile.methodCount; i++) {
                classFile.methods[i] = new MethodInfo();
                classFile.methods[i].accessFlags = readU2ToShort();
                classFile.methods[i].nameIndex = readU2ToShort();
                classFile.methods[i].descriptorIndex = readU2ToShort();
                classFile.methods[i].attributesCount = readU2ToShort();
                if (classFile.methods[i].attributesCount > 0) {
                    classFile.methods[i].attributes = new AttributeInfo[classFile.methods[i].attributesCount];
                    for (int j = 0; j < classFile.methods[i].attributesCount; j++) {
                        short attributeNameIndex = readU2ToShort();
                        classFile.methods[i].attributes[j] = createAttribute(attributeNameIndex);
                    }
                }
            }
        }
        classFile.attributesCount = readU2ToShort();
        if (classFile.attributesCount > 0) {
            classFile.attributes = new AttributeInfo[classFile.attributesCount];
            for (int i = 0; i < classFile.attributesCount; i++) {
                short attributeNameIndex = readU2ToShort();
                classFile.attributes[i] = createAttribute(attributeNameIndex);
            }
        }
        return classFile;
    }

    public byte readU1ToByte() {
        byte a = classBytes[index];
        index++;
        return a;
    }

    public short readU2ToShort() {
        short a = 0;
        for (int i = 0; i < 2; i++) {
            a = (short) (a << 8 | (classBytes[index + i] & 0xFF));
        }
        index += 2;
        return a;
    }

    public int readU4ToInt() {
        int a = 0;
        for (int i = 0; i < 4; i++) {
            a = a << 8 | (classBytes[index + i] & 0x000000FF);
        }
        index += 4;
        return a;
    }

    public long readU8ToLong() {
        long a = 0;
        for (int i = index; i < 8; i++) {
            a = a << 8 | (long) (classBytes[index + i] & 0x00FF);
        }
        index += 8;
        return a;
    }

    public byte[] readBytes(Number length) {
        byte[] bytes = Arrays.copyOfRange(classBytes, index, index + length.intValue());
        index += length.intValue();
        return bytes;
    }

    public AttributeInfo createAttribute(short attributeNameIndex) {
        ConstantInfo constantInfo = classFile.constantPool[attributeNameIndex];
        ConstantUtf8 constantUtf8 = (ConstantUtf8) constantInfo;
        String attributeName = constantUtf8.getValue();
        AttributeInfo attributeInfo = attributeFactory.create(attributeName);
        attributeInfo.readBytes(this);
        return attributeInfo;
    }

}
