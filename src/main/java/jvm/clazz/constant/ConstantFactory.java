package jvm.clazz.constant;


import ioc.anno.Compontment;
import jvm.clazz.ConstantTagEnum;

/**
 * 常量工厂方法
 */
@Compontment
public class ConstantFactory {

    public ConstantInfo create(byte tag, ConstantInfo[] constantInfos) {
        ConstantTagEnum tagEnum = ConstantTagEnum.of(tag);
        switch (tagEnum) {
            case CONSTANT_utf8_info:
                return new ConstantUtf8(tag);
            case CONSTANT_Integer_info:
                return new ConstantInteger(tag);
            case CONSTANT_Float_info:
                return new ConstantFloat(tag);
            case CONSTANT_Long_info:
                return new ConstantLong(tag);
            case CONSTANT_Double_info:
                return new ConstantDouble(tag);
            case CONSTANT_Class_info:
                return new ConstantClass(tag, constantInfos);
            case CONSTANT_String_info:
                return new ConstantString(tag);
            case CONSTANT_Fieldref_info:
                return new ConstantFieldref(tag, constantInfos);
            case CONSTANT_Methodref_info:
                return new ConstantMethodref(tag, constantInfos);
            case CONSTANT_InterfaceMethodref_info:
                return new ConstantInterfaceMethodref(tag, constantInfos);
            case CONSTANT_NameAndType_info:
                return new ConstantNameAndType(tag, constantInfos);
            case CONSTANT_MethodHandle_info:
                return new ConstantMethodHandle(tag);
            case CONSTANT_MethodType_info:
                return new ConstantMethodType(tag);
            case CONSTANT_InvokeDynamic_info:
                return new ConstantInvokeDynamic(tag);
            default:
                return null;
        }

    }

}
