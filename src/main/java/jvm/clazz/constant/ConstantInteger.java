package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-25
 */
public class ConstantInteger extends ConstantInfo {

    int value;

    @Override
    public void readBytes(ClassReader reader) {
        value = reader.readU4ToInt();
    }

}
