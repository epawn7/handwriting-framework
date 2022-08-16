package jvm.instructions.base;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;

/**
 * 跳转指令
 */
public abstract class BranchInstruction implements Instruction {

    /**
     * 跳转偏移量
     */
    protected int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readShort();
    }

}
