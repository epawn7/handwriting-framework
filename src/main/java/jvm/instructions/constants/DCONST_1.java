package jvm.instructions.constants;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 把double型1推入操作数栈顶
 */
public class DCONST_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushDouble(1.0d);
    }

}
