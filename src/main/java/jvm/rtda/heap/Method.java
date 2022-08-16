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
