package jvm.instructions.loads;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表float数据,
 * 再将数据放入到操作栈中
 */
public class FLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float val = frame.getLocalVars().getFloat(1);
        frame.getOperandStack().pushFloat(val);
    }

}
