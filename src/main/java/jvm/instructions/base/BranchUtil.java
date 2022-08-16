package jvm.instructions.base;

import jvm.rtda.Frame;

/**
 *
 */
public class BranchUtil {

    public static void branch(Frame frame, int offset) {
        int pc = frame.getThread().getPc();
        int nextPC = pc + offset;
        frame.setNextPC(nextPC);
    }

}
