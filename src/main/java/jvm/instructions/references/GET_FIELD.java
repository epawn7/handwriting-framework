package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Field;
import jvm.rtda.heap.ref.FieldRef;

/**
 * 获取对象的实例变量值，然后推入操作数栈
 * 需要两个操作数。第一个操作数是uint16索引,第二个操作数是对象引用
 */
public class GET_FIELD extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();

        FieldRef fieldRef = (FieldRef) cp.getConstant(index).getVal();
        Field field = fieldRef.resolvedField();
        if (field.isStastic()) {
            throw new RuntimeException("类编译错误");
        }
        OperandStack stack = frame.getOperandStack();
        Object object = stack.popRef();
        if (object == null) {
            throw new RuntimeException("空指针");
        }
        switch (field.getDescriptor().charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                int iVal = object.getFields().getInt(field.getSlotId());
                stack.pushInt(iVal);
                break;
            case 'F':
                float fVal = object.getFields().getFloat(field.getSlotId());
                stack.pushFloat(fVal);
                break;
            case 'J':
                long lVal = object.getFields().getLong(field.getSlotId());
                stack.pushLong(lVal);
                break;
            case 'D':
                double dVal = object.getFields().getDouble(field.getSlotId());
                stack.pushDouble(dVal);
                break;
            case 'L':
            case '[':
                Object oVal = object.getFields().getRef(field.getSlotId());
                stack.pushRef(oVal);
                break;
        }
    }

}
