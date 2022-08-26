package jvm.rtda.heap;

import java.util.ArrayList;
import jvm.clazz.MethodInfo;
import jvm.clazz.attribute.CodeAttribute;
import jvm.clazz.constant.ConstantInfo;
import jvm.clazz.constant.ConstantUtf8;

/**
 * 方法信息
 */
public class Method extends ClassMember {

    int maxStack;

    int maxLocals;

    int argSlotCount;

    MethodDescriptor methodDescriptor;


    byte[] code;

    public Method(Clazz clazz, MethodInfo methodInfo, ConstantInfo[] constantPool) {
        this.clazz = clazz;
        this.accessFlag = methodInfo.getAccessFlags();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            this.maxStack = codeAttribute.getMaxStack();
            this.maxLocals = codeAttribute.getMaxLocals();
            this.code = codeAttribute.getCode();
        }
        ConstantUtf8 nameValue = (ConstantUtf8) constantPool[methodInfo.getNameIndex()];
        this.name = nameValue.getValue();
        ConstantUtf8 descriptorValue = (ConstantUtf8) constantPool[methodInfo.getDescriptorIndex()];
        this.descriptor = descriptorValue.getValue();
        this.methodDescriptor = new MethodDescriptor(this.descriptor);
        this.argSlotCount = calcArgSlotCount(this.methodDescriptor.parameterTypes);
        if (this.isNative()) {
            injectCodeAttribute(methodDescriptor.returnType);
        }
    }

    public static Method[] newMethods(Clazz clazz, MethodInfo[] methodInfos, ConstantInfo[] constantPool) {
        if (methodInfos == null) {
            return new Method[0];
        }
        Method[] methods = new Method[methodInfos.length];
        for (int i = 0; i < methodInfos.length; i++) {
            Method method = new Method(clazz, methodInfos[i], constantPool);
            methods[i] = method;
        }
        return methods;
    }

    private int calcArgSlotCount(ArrayList<String> argList) {
        int count = 0;
        if (!isStastic()) {
            //非statis方法,第一个参数为this
            count++;
        }
        for (String s : argList) {
            count++;
            if (s.equals("D") || s.equals("J")) {
                count++;
            }
        }
        return count;
    }

    private void injectCodeAttribute(String returnType) {
        this.maxStack = 4;
        this.maxLocals = this.argSlotCount;
        switch (returnType.charAt(0)) {
            case 'V':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb1};
                break;
            case 'D':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xaf};
                break;
            case 'F':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xae};
                break;
            case 'J':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xad};
                break;
            case 'L':
            case '[':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb0};
                break;
            default:
                this.code = new byte[]{(byte) 0xfe, (byte) 0xac};
        }
    }


    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public int getArgSlotCount() {
        return argSlotCount;
    }

    public boolean isNative() {
        return 0 != (accessFlag & AccessFlagConst.ACC_NATIVE);
    }

}
