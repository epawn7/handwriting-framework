package jvm.instructions.control;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.Thread;

/**
 * 返回float
 */
public class FRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame popFrame = thread.popFrame();
        OperandStack stack = thread.topFrame().getOperandStack();
        stack.pushFloat(popFrame.getOperandStack().popFloat());
    }

}
