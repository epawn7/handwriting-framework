package jvm.instructions.constants;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.rtda.Frame;

/**
 * 获取一个short型整数，扩展成int型，然后推入栈顶
 */
public class SIPUSH implements Instruction {

    int val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = reader.readShort();
    }

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushInt(val);
    }

}
