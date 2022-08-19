package jvm.instructions.stores;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;

/**
 * <t>astore系列指令按索引给数组元素赋值。
 */
public class LASTORE extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long val = stack.popLong();
        int index = stack.popInt();
        Object arrayObj = stack.popRef();

        //校验空指针
        if (arrayObj == null) {
            throw new RuntimeException("空指针");
        }
        //校验下标是否越界
        if (index < 0 || index >= arrayObj.arrayLength()) {
            throw new RuntimeException("数组越界");
        }
        arrayObj.getLongs()[index] = val;
    }

}
