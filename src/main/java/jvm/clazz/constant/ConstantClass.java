package jvm.clazz.constant;

import jvm.clazz.ClassReader;

/**
 * CONSTANT_Class_info 常量表示类或者接口的符号引用，指向是接口或者类名。
 */
public class ConstantClass extends ConstantInfo {

    /**
     * 常量池索引,想要获取class的全限定名,
     * 需要通过 name_index 先得到常量池中的 CONSTANT_Utf8_info，
     * 然后在获取其中的值
     */
    short nameIndex;

    ConstantInfo[] constantInfos;

    public ConstantClass(byte type, ConstantInfo[] constantInfos) {
        super(type);
        this.constantInfos = constantInfos;
    }

    @Override
    public void readBytes(ClassReader reader) {
        nameIndex = reader.readU2ToShort();
    }

    public String getClassName() {
        ConstantUtf8 constantUtf8 = (ConstantUtf8) constantInfos[nameIndex];
        return constantUtf8.getValue();
    }

    public short getNameIndex() {
        return nameIndex;
    }

}
