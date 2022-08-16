package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * double类型相乘
 */
public class DMUL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double v1 = stack.popDouble();
        double v2 = stack.popDouble();
        stack.pushDouble(v1 * v2);
    }

}
