package jvm.instructions.conversions;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int转float
 */
public class I2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v = stack.popInt();
        stack.pushFloat(v);
    }

}
