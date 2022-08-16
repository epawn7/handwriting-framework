package jvm.instructions.math;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * long右位移
 */
public class LUSHR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        //左移比特位
        int v1 = stack.popInt();
        //待处理数据
        long v2 = stack.popLong();
        //v1 & 0x1f  表示long最多64位,所以v1最大值只能为32,取前6位就够了
        stack.pushLong(v2 >>> (v1 & 0x3f));
    }

}
