package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.ref.ClassRef;

/**
 * 指令判断对象是否是某个类的实例
 *
 * 第一个操作数是uint16索引，从方法的字节码中获取，通过这个索引可以从当前类的运行时常量池中找到一个类符号引用。
 * 第二个操作数是对象引用，从操作数栈中弹出。
 */
public class INSTANCE_OF extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        OperandStack stack = frame.getOperandStack();
        ClassRef classRef = (ClassRef) cp.getConstant(index).getVal();
        Object object = stack.popRef();

        if (object == null) {
            stack.pushInt(0);
            return;
        }
        if (object.isInstanceOf(classRef.getClazz())) {
            stack.pushInt(1);
        } else {
            stack.pushInt(0);
        }

    }

}
