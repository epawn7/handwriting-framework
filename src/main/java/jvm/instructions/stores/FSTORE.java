package jvm.instructions.stores;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 存储指令把变量从操作数栈顶弹出，然后存入局部变量表。
 */
public class FSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        float val = frame.getOperandStack().popFloat();
        frame.getLocalVars().setFloat(index, val);
    }

}
