package jvm.instructions.stack;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.Slot;

/**
 * 对栈顶的两个slot进行复制
 *
 * b a =>  b a b a;
 */
public class DUP2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot s1 = operandStack.popSlot();
        Slot s2 = operandStack.popSlot();
        operandStack.pushSlot(s2);
        operandStack.pushSlot(s1);
        operandStack.pushSlot(s2);
        operandStack.pushSlot(s1);
    }

}
