package jvm.instructions.comparisons;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * lcmp指令用于比较long变量
 */
public class LCMP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long v2 = stack.popLong();
        long v1 = stack.popLong();
        if (v1 > v2) {
            stack.pushInt(1);
        } else if (v1 == v2) {
            stack.pushInt(0);
        } else {
            stack.pushInt(-1);
        }
    }

}
