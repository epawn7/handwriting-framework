package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 字符串常量,指向constant_utf8常量
 */
public class ConstantString extends ConstantInfo {

    /**
     * 指向常量池的索引
     */
    short stringIndex;

    public ConstantString(byte type) {
        super(type);
    }

    @Override
    public void readBytes(ClassReader reader) {
        this.stringIndex = reader.readU2ToShort();
    }

    public short getStringIndex() {
        return stringIndex;
    }

}
