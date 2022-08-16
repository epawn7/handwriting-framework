package jvm.instructions.constants;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 把long型0推入操作数栈顶
 */
public class LCONST_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushLong(0L);
    }

}
