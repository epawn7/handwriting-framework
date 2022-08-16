package jvm.instructions.base;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;

/**
 * 存储和加载类指令需要根据索引存取局部变量表，索引由单字节操作数给出。
 * 把这类指令抽象成Index8Instruction结构体，
 * 用Index字段表示局部变量表索引。
 */
public abstract class Index8Instruction implements Instruction {

    /**
     * 局部变量表索引
     */
    public int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint8();
    }

}
