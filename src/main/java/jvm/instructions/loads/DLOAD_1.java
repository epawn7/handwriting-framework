package jvm.instructions.loads;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表double数据,
 * 再将数据放入到操作栈中
 */
public class DLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.getLocalVars().getDouble(1);
        frame.getOperandStack().pushDouble(val);
    }

}
