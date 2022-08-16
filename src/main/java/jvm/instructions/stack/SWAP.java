package jvm.instructions.stack;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.Slot;

/**
 * 交换栈顶的两个变量
 */
public class SWAP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot s1 = stack.popSlot();
        Slot s2 = stack.popSlot();
        stack.pushSlot(s1);
        stack.pushSlot(s2);
    }

}
