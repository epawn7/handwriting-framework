package jvm.instructions.loads;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;

/**
 * <t>aload系列指令按索引取数组元素值，然后推入操作数栈。
 */
public class SALOAD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        //获取数组下表索引
        int index = stack.popInt();
        //获取数组对象
        Object array = stack.popRef();
        //校验空指针
        if (array == null) {
            throw new RuntimeException("空指针");
        }
        //校验下标是否越界
        if (index < 0 || index >= array.arrayLength()) {
            throw new RuntimeException("数组越界");
        }
        //获取对象
        short data = array.getShorts()[index];
        stack.pushInt(data);
    }

}
