package jvm.instructions.constants;

import jvm.clazz.ConstantTagEnum;
import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Constant;
import jvm.rtda.heap.ConstantPool;

/**
 * 令从运行时常量池中加载常量值，并把它推入操作数栈
 */
public class LDC2_W extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        Constant constant = cp.getConstant(index);
        ConstantTagEnum tagEnum = ConstantTagEnum.of((byte) constant.getType());
        switch (tagEnum) {
            case CONSTANT_Long_info:
                stack.pushLong((long) constant.getVal());
                break;
            case CONSTANT_Double_info:
                stack.pushDouble((double) constant.getVal());
                break;
            default:
                throw new RuntimeException("ClassFormatError");
        }
    }

}
