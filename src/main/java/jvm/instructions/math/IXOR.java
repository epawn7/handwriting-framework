package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int异或运算
 */
public class IXOR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v1 = stack.popInt();
        int v2 = stack.popInt();
        stack.pushInt(v1 ^ v2);
    }

}
