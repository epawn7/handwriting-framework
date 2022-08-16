package jvm.instructions.stack;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * pop指令只能用于弹出int、float等占用一个操作数栈位置的变 量
 */
public class POP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().popSlot();
    }

}
