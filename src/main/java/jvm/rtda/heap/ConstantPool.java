package jvm.rtda.heap;

import jvm.clazz.ConstantTagEnum;
import jvm.clazz.constant.ConstantClass;
import jvm.clazz.constant.ConstantDouble;
import jvm.clazz.constant.ConstantFieldref;
import jvm.clazz.constant.ConstantFloat;
import jvm.clazz.constant.ConstantInfo;
import jvm.clazz.constant.ConstantInteger;
import jvm.clazz.constant.ConstantInterfaceMethodref;
import jvm.clazz.constant.ConstantLong;
import jvm.clazz.constant.ConstantMethodref;
import jvm.clazz.constant.ConstantString;
import jvm.clazz.constant.ConstantUtf8;
import jvm.rtda.heap.ref.ClassRef;
import jvm.rtda.heap.ref.FieldRef;
import jvm.rtda.heap.ref.InterfaceMethodRef;
import jvm.rtda.heap.ref.MethodRef;

/**
 * 运行时常量池
 */
public class ConstantPool {

    Clazz clazz;

    Constant[] constants;

    public ConstantPool(Clazz clazz, ConstantInfo[] constantPool) {
        this.clazz = clazz;
        this.constants = new Constant[constantPool.length];
        for (int i = 1; i < constantPool.length; i++) {
            byte type = constantPool[i].getType();
            ConstantTagEnum constantTag = ConstantTagEnum.of(type);
            switch (constantTag) {
                case CONSTANT_Integer_info:
                    ConstantInteger constantInteger = (ConstantInteger) constantPool[i];
                    constants[i] = new Constant(constantInteger.getValue(), type);
                    break;
                case CONSTANT_Float_info:
                    ConstantFloat constantFloat = (ConstantFloat) constantPool[i];
                    constants[i] = new Constant(constantFloat.getValue(), type);
                    break;
                case CONSTANT_Long_info:
                    ConstantLong constantLong = (ConstantLong) constantPool[i];
                    constants[i] = new Constant(constantLong.getValue(), type);
                    i++;
                    break;
                case CONSTANT_Double_info:
                    ConstantDouble constantDouble = (ConstantDouble) constantPool[i];
                    constants[i] = new Constant(constantDouble.getValue(), type);
                    i++;
                    break;
                case CONSTANT_String_info:
                    ConstantString constantString = (ConstantString) constantPool[i];
                    constants[i] = new Constant(
                            ((ConstantUtf8) constantPool[constantString.getStringIndex()]).getValue(), type);
                    break;
                case CONSTANT_Class_info:
                    ConstantClass constantClass = (ConstantClass) constantPool[i];
                    constants[i] = new Constant(new ClassRef(this, constantClass), type);
                    break;
                case CONSTANT_Fieldref_info:
                    ConstantFieldref constantFieldref = (ConstantFieldref) constantPool[i];
                    constants[i] = new Constant(new FieldRef(this, constantFieldref), type);
                    break;
                case CONSTANT_Methodref_info:
                    ConstantMethodref constantMethodref = (ConstantMethodref) constantPool[i];
                    constants[i] = new Constant(new MethodRef(this, constantMethodref), type);
                    break;
                case CONSTANT_InterfaceMethodref_info:
                    ConstantInterfaceMethodref constantInterfaceMethodref =
                            (ConstantInterfaceMethodref) constantPool[i];
                    constants[i] = new Constant(new InterfaceMethodRef(this, constantInterfaceMethodref), type);
                    break;
            }
        }
    }


    public Clazz getClazz() {
        return clazz;
    }

    public Constant getConstant(int index) {
        return constants[index];
    }

}
