package jvm.instructions.constants;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 把int型3推入操作数栈顶
 */
public class ICONST_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushInt(3);
    }

}
