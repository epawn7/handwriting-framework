package jvm.instructions.stores;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;

/**
 * 存储指令把变量从操作数栈顶弹出，然后存入局部变量表。
 */
public class ASTORE_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Object val = frame.getOperandStack().popRef();
        frame.getLocalVars().setRef(1, val);
    }

}
