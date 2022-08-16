package jvm.instructions.loads;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表int数据,
 * 再将数据放入到操作栈中
 */
public class ILOAD_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int val = frame.getLocalVars().getInt(0);
        frame.getOperandStack().pushInt(val);
    }

}
