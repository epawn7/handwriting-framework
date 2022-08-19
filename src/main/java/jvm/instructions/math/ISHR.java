package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * int右位移
 */
public class ISHR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        //左移比特位
        int v1 = stack.popInt();
        //待处理数据
        int v2 = stack.popInt();
        //v1 & 0x1f  表示int最多32位,所以v1最大值只能为32,取前5位就够了
        stack.pushInt(v2 >> (v1 & 0x1f));
    }

}
