package jvm.instructions.extended;

import jvm.instructions.base.BranchInstruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;
import jvm.rtda.Object;

/**
 * 根据引用是否是null进行跳转
 */
public class IFNULL extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        Object object = frame.getOperandStack().popRef();
        if (object == null) {
            BranchUtil.branch(frame, super.offset);
        }
    }

}
