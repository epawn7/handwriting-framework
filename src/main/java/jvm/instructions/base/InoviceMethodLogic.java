package jvm.instructions.base;

import jvm.rtda.Frame;
import jvm.rtda.Thread;
import jvm.rtda.heap.Method;

public class InoviceMethodLogic {

    public static void invokeMethod(Frame frame, Method method) {
        Thread thread = frame.getThread();
        Frame newFrame = new Frame(thread, method);
        thread.pushFrame(newFrame);
        if (method.isNative()) {
            if (method.getName().equals("registerNatives")) {
                thread.popFrame();
            } else {
                throw new RuntimeException("native方法不支持");
            }
        }
        int argCount = method.getArgSlotCount();
        if (argCount > 0) {
            for (int i = argCount - 1; i >= 0; i--) {
                newFrame.getLocalVars().setSlot(i, frame.getOperandStack().popSlot());
            }
        }
    }

}
