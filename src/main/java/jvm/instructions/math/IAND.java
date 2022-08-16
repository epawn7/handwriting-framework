package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int 与计算
 */
public class IAND extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v1 = stack.popInt();
        int v2 = stack.popInt();
        stack.pushInt(v2 & v1);
    }

}
