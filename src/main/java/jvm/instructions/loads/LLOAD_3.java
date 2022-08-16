package jvm.instructions.loads;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 根据索引获取本地变量表long数据,
 * 再将数据放入到操作栈中
 */
public class LLOAD_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val = frame.getLocalVars().getLong(3);
        frame.getOperandStack().pushLong(val);
    }

}
