package jvm.instructions.conversions;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * double转long
 */
public class D2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double v = stack.popDouble();
        stack.pushLong((long) v);
    }

}
