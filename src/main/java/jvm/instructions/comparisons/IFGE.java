package jvm.instructions.comparisons;

import jvm.instructions.base.BranchInstruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;

/**
 * if<cond>指令把操作数栈顶的int变量弹出，然后跟0进行比较， 满足条件则跳转
 */
public class IFGE extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        int val = frame.getOperandStack().popInt();
        if (val >= 0) {
            BranchUtil.branch(frame, super.offset);
        }
    }

}
