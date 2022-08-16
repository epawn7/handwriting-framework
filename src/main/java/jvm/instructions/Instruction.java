package jvm.instructions;

import jvm.rtda.Frame;

/**
 * 2022-08-01
 */
public interface Instruction {

    /**
     * 从字节码中提取操作数
     */
    void fetchOperands(BytecodeReader reader);

    /**
     * 执行指令逻辑
     */
    void execute(Frame frame);

}
