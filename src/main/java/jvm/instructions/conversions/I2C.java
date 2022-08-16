package jvm.instructions.conversions;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int转char
 */
public class I2C extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v = stack.popInt();
        stack.pushInt((char) v);
    }

}
