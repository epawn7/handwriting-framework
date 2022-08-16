package jvm.instructions.control;

import jvm.instructions.base.BranchInstruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;

/**
 * goto指令进行无条件跳转
 */
public class GOTO extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        BranchUtil.branch(frame, super.offset);
    }

}
