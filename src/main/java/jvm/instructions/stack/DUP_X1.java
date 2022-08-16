package jvm.instructions.stack;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.Slot;

/**
 * DUP_X1 指令 先将栈顶的两个变量交换,然后再将原栈顶元素添加到栈顶 ab => bab;
 */
public class DUP_X1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot s1 = operandStack.popSlot();
        Slot s2 = operandStack.popSlot();
        operandStack.pushSlot(s1);
        operandStack.pushSlot(s2);
        operandStack.pushSlot(s1);
    }

}
