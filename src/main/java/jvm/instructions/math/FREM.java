package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * float数求余
 */
public class FREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float v1 = stack.popFloat();
        float v2 = stack.popFloat();
        stack.pushDouble(v2 % v1);
    }

}
