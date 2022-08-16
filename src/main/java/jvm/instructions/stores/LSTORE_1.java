package jvm.instructions.stores;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 存储指令把变量从操作数栈顶弹出，然后存入局部变量表。
 */
public class LSTORE_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val = frame.getOperandStack().popLong();
        frame.getLocalVars().setLong(1, val);
    }

}
