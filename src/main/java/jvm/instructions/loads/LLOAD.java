package jvm.instructions.loads;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表long数据,
 * 再将数据放入到操作栈中
 */
public class LLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        long val = frame.getLocalVars().getLong(super.index);
        frame.getOperandStack().pushLong(val);
    }

}
