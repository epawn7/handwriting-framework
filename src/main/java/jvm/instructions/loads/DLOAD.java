package jvm.instructions.loads;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表double数据,
 * 再将数据放入到操作栈中
 */
public class DLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.getLocalVars().getDouble(super.index);
        frame.getOperandStack().pushDouble(val);
    }

}
