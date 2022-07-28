package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * 常量池信息
 * 分为两种: 字面量和符号引用
 *
 * @author jinfan 2022-07-25
 */
public abstract class ConstantInfo {

    public abstract void readBytes(ClassReader reader);

}
