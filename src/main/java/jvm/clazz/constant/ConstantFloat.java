package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-25
 */
public class ConstantFloat extends ConstantInfo {

    float value;

    @Override
    public void readBytes(ClassReader reader) {
        int temp = reader.readU4ToInt();
        value = Float.intBitsToFloat(temp);
    }

}
