package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.ref.ClassRef;

/**
 * 创建引用类型数组
 * anewarray指令也需要两个操作数。
 * 第一个操作数是uint16索引，来自字节码。
 * 通过这个索引可以从当前类的运行时常量池中找到一个类符号引用，
 * 解析这个符号引用就可以得到数组元素的类。
 * 第二个操作数是数组长度，从操作数栈中弹出。
 */
public class ANEW_ARRAY extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(this.index).getVal();
        Clazz clazz = classRef.resolvedClass();
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new RuntimeException("数组大小小于0");
        }
        String arrayName = clazz.getArrayName();
        Clazz arrayClazz = clazz.getClassLoader().loadClass(arrayName);
        Object array = new Object(arrayClazz, count);
        stack.pushRef(array);
    }

}
