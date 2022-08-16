package jvm.instructions.base;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;

/**
 * 有一些指令需要访问运行时常量池，常量池索引由两字节操作数给出。
 * 把这类指令抽象成Index16Instruction结构体，
 * 用Index字段表示常量池索引。
 */
public abstract class Index16Instruction implements Instruction {

    public int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint16();
    }

}
