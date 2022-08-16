package jvm.instructions.loads;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表int数据,
 * 再将数据放入到操作栈中
 */
public class ILOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        int val = frame.getLocalVars().getInt(super.index);
        frame.getOperandStack().pushInt(val);
    }

}
