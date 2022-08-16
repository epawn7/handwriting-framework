package jvm.instructions.stores;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 存储指令把变量从操作数栈顶弹出，然后存入局部变量表。
 */
public class DSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.getOperandStack().popDouble();
        frame.getLocalVars().setDouble(index, val);
    }

}
