package jvm.instructions.references;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;

/**
 * 用于获取数组长度
 *
 * 只需要一个操作数，即从操作数栈顶弹出的数组引用。
 */
public class ARRAY_LENGTH extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Object o = stack.popRef();
        if (o == null) {
            throw new RuntimeException("空指针");
        }
        stack.pushInt(o.arrayLength());
    }

}
