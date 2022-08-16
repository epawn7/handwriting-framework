package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * float类型相加
 */
public class FADD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float v1 = stack.popFloat();
        float v2 = stack.popFloat();
        stack.pushFloat(v1 + v2);
    }

}
