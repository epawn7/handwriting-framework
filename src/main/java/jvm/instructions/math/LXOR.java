package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * long异或运算
 */
public class LXOR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long v1 = stack.popLong();
        long v2 = stack.popLong();
        stack.pushLong(v1 ^ v2);
    }

}
