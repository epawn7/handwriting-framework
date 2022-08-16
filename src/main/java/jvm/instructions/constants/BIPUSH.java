package jvm.instructions.constants;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.rtda.Frame;

/**
 * 操作数中获取一个byte型整数，
 * 扩展成int型，然后推入栈顶
 */
public class BIPUSH implements Instruction {

    int val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = (reader.readByte() & 0x000000FF);
    }

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushInt(val);
    }

}
