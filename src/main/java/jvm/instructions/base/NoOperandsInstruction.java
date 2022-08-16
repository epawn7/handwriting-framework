package jvm.instructions.base;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;

/**
 * 表示没有操作数的指令。
 */
public abstract class NoOperandsInstruction implements Instruction {

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //不执行指令
    }

}
