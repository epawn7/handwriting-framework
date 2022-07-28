package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-25
 */
public class ConstantDouble extends ConstantInfo {

    double value;

    @Override
    public void readBytes(ClassReader reader) {
        long temp = reader.readU8ToLong();
        value = Double.longBitsToDouble(temp);
    }

}
