package jvm.instructions.references;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.Thread;

public class ATHROW extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Object ex = frame.getOperandStack().popRef();
        if (ex == null) {
            throw new RuntimeException("空指针");
        }
        if (!findAndGotoExceptionHandler(frame.getThread(), ex)) {

        }
    }

    private boolean findAndGotoExceptionHandler(Thread thread, Object ex) {
        while (!thread.isStackEmpty()) {
            Frame frame = thread.topFrame();
            int pc = thread.getPc();
            int handlerPc = frame.getMethod().findExceptionHandler(ex.getClazz(), pc);
            if (handlerPc > 0) {
                OperandStack stack = frame.getOperandStack();
                stack.clear();
                stack.pushRef(ex);
                frame.setNextPC(handlerPc);
                return true;
            }
            thread.popFrame();
        }
        return false;
    }

    private void handleUncaughtException(Thread thread, Object ex) {
        thread.clearStack();

    }

}
