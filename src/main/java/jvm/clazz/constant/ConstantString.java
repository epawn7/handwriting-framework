package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-27
 */
public class ConstantString extends ConstantInfo {

    /**
     * 指向常量池的索引
     */
    short stringIndex;

    @Override
    public void readBytes(ClassReader reader) {
        this.stringIndex = reader.readU2ToShort();
    }

}
