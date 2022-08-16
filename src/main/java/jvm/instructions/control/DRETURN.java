package jvm.instructions.control;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.Thread;

/**
 * 返回double
 */
public class DRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame popFrame = thread.popFrame();
        OperandStack stack = thread.topFrame().getOperandStack();
        stack.pushDouble(popFrame.getOperandStack().popDouble());
    }

}
