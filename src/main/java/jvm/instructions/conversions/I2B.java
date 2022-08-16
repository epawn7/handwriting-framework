package jvm.instructions.conversions;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int转byte
 */
public class I2B extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v = stack.popInt();
        stack.pushInt((byte) v);
    }

}
