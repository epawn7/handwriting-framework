package jvm.instructions.stores;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;

/**
 * 存储指令把变量从操作数栈顶弹出，然后存入局部变量表。
 */
public class ASTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Object val = frame.getOperandStack().popRef();
        frame.getLocalVars().setRef(index, val);
    }

}
