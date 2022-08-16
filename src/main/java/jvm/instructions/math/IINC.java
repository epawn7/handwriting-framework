package jvm.instructions.math;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.rtda.Frame;
import jvm.rtda.LocalVars;

/**
 * 给局部变量表中的int变量增加常量值
 */
public class IINC implements Instruction {

    //本地变量表索引
    int index;

    //常量值
    int val;


    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint8();
        val = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(index);
        localVars.setInt(index, val + this.val);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setVal(int val) {
        this.val = val;
    }

}
