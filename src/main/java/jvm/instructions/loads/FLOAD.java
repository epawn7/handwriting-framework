package jvm.instructions.loads;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表float数据,
 * 再将数据放入到操作栈中
 */
public class FLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        float val = frame.getLocalVars().getFloat(super.index);
        frame.getOperandStack().pushFloat(val);
    }

}
