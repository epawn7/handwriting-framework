package jvm.clazz.constant;


import ioc.anno.Compontment;
import jvm.clazz.ConstantTagEnum;

/**
 * @author jinfan 2022-07-26
 */
@Compontment
public class ConstantFactory {

    public ConstantInfo create(byte tag) {
        ConstantTagEnum tagEnum = ConstantTagEnum.of(tag);
        switch (tagEnum) {
            case CONSTANT_utf8_info:
                return new ConstantUtf8();
            case CONSTANT_Integer_info:
                return new ConstantInteger();
            case CONSTANT_Float_info:
                return new ConstantFloat();
            case CONSTANT_Long_info:
                return new ConstantLong();
            case CONSTANT_Double_info:
                return new ConstantDouble();
            case CONSTANT_Class_info:
                return new ConstantClass();
            case CONSTANT_String_info:
                return new ConstantString();
            case CONSTANT_Fieldref_info:
                return new ConstantFieldref();
            case CONSTANT_Methodref_info:
                return new ConstantMethodref();
            case CONSTANT_InterfaceMethodref_info:
                return new ConstantInterfaceMethodref();
            case CONSTANT_NameAndType_info:
                return new ConstantNameAndType();
            case CONSTANT_MethodHandle_info:
                return new ConstantMethodHandle();
            case CONSTANT_MethodType_info:
                return new ConstantMethodType();
            case CONSTANT_InvokeDynamic_info:
                return new ConstantInvokeDynamic();
            default:
                return null;
        }

    }

}
