package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * @author jinfan 2022-07-27
 */
public class ConstantMethodType extends ConstantInfo {

    short descriptorIndex;


    @Override
    public void readBytes(ClassReader reader) {
        this.descriptorIndex = reader.readU2ToShort();
    }

}
