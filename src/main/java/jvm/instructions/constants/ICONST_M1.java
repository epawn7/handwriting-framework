package jvm.instructions.constants;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 把int型-1推入操作数栈顶
 */
public class ICONST_M1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushInt(-1);
    }

}
