package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int值求余
 */
public class IREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v1 = stack.popInt();
        int v2 = stack.popInt();
        if (v1 == 0) {
            throw new RuntimeException("除数为0");
        }
        stack.pushInt(v2 % v1);
    }

}
