package jvm.instructions.stack;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.Slot;

/**
 * a b c => b c a b c
 */
public class DUP2_X1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot s1 = operandStack.popSlot();
        Slot s2 = operandStack.popSlot();
        Slot s3 = operandStack.popSlot();
        operandStack.pushSlot(s2);
        operandStack.pushSlot(s1);
        operandStack.pushSlot(s3);
        operandStack.pushSlot(s2);
        operandStack.pushSlot(s1);
    }

}
