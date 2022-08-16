package jvm.instructions.extended;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;

/**
 * goto_w指令和goto指令的唯一区别就是索引从2字节变成了4字节
 */
public class GOTO_W implements Instruction {

    int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readInt();
    }

    @Override
    public void execute(Frame frame) {
        BranchUtil.branch(frame, offset);
    }

}
