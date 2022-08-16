package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Field;
import jvm.rtda.heap.Method;
import jvm.rtda.heap.ref.FieldRef;

/**
 * 给实例变量赋值，它需要三个操作数。
 * 前两个操作数是常量池索引和变量值,用法和putstatic一样。
 * 第三个操作数是 对象引用，从操作数栈中弹出。
 */
public class PUT_FIELD extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Method method = frame.getMethod();
        ConstantPool cp = method.getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(this.index).getVal();
        Field field = fieldRef.resolvedField();
        if (field.isStastic()) {
            throw new RuntimeException("类编译异常");
        }
        if (field.isFinal()) {
            if (method.getClazz() != field.getClazz() || !method.getName().equals("<init>")) {
                throw new RuntimeException("非法访问");
            }
        }
        String descriptor = field.getDescriptor();
        OperandStack stack = frame.getOperandStack();
        Object object;
        switch (descriptor) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                int val = stack.popInt();
                object = stack.popRef();
                if (object == null) {
                    throw new RuntimeException("空指针");
                }
                object.getFields().setInt(field.getSlotId(), val);
                break;
            case "F":
                float floatVal = stack.popFloat();
                object = stack.popRef();
                if (object == null) {
                    throw new RuntimeException("空指针");
                }
                object.getFields().setFloat(field.getSlotId(), floatVal);
                break;
            case "J":
                long longVal = stack.popLong();
                object = stack.popRef();
                if (object == null) {
                    throw new RuntimeException("空指针");
                }
                object.getFields().setLong(field.getSlotId(), longVal);
                break;
            case "D":
                double doubleVal = stack.popDouble();
                object = stack.popRef();
                if (object == null) {
                    throw new RuntimeException("空指针");
                }
                object.getFields().setDouble(field.getSlotId(), doubleVal);
                break;
            case "L":
            case "[":
                Object ref = stack.popRef();
                object = stack.popRef();
                if (object == null) {
                    throw new RuntimeException("空指针");
                }
                object.getFields().setRef(field.getSlotId(), ref);
                break;
        }
    }

}
