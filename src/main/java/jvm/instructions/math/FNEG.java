package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * float类型取反数
 */
public class FNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float v = stack.popFloat();
        stack.pushFloat(-v);
    }

}
