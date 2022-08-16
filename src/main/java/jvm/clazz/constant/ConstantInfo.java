package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 常量池信息
 * 分为两种: 字面量和符号引用
 */
public abstract class ConstantInfo {

    byte type;

    public ConstantInfo(byte type) {
        this.type = type;
    }

    public abstract void readBytes(ClassReader reader);

    public byte getType() {
        return type;
    }

}
