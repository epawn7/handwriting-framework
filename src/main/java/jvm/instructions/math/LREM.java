package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * long值求余
 */
public class LREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long v1 = stack.popLong();
        long v2 = stack.popLong();
        if (v1 == 0) {
            throw new RuntimeException("除数为0");
        }
        stack.pushLong(v2 % v1);
    }

}
