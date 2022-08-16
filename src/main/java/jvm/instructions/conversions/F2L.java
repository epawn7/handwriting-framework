package jvm.instructions.conversions;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * float转long
 */
public class F2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float v = stack.popFloat();
        stack.pushLong((long) v);
    }

}
