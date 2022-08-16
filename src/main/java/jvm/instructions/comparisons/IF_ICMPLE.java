package jvm.instructions.comparisons;

import jvm.instructions.base.BranchInstruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;

/**
 * if_icmp<cond>指令把栈顶的两个int变量弹出，然后进行比较，满足条件则跳转。
 */
public class IF_ICMPLE extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        if (v1 <= v2) {
            BranchUtil.branch(frame, super.offset);
        }
    }

}
