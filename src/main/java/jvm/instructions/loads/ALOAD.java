package jvm.instructions.loads;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;

/**
 * 根据索引获取本地变量表Ref数据,
 * 再将数据放入到操作栈中
 */
public class ALOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Object val = frame.getLocalVars().getRef(super.index);
        frame.getOperandStack().pushRef(val);
    }

}
