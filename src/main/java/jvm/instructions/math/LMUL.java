package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * long类型相乘
 */
public class LMUL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long v1 = stack.popLong();
        long v2 = stack.popLong();
        stack.pushFloat(v1 * v2);
    }

}
