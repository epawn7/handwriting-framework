package jvm.instructions.comparisons;

import jvm.instructions.base.BranchInstruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;

/**
 * if_icmp<cond>指令把栈顶的两个int变量弹出，然后进行比较，满足条件则跳转。
 */
public class IF_ACMPNE extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Object v2 = stack.popRef();
        Object v1 = stack.popRef();
        if (v1 != v2) {
            BranchUtil.branch(frame, super.offset);
        }
    }

}
