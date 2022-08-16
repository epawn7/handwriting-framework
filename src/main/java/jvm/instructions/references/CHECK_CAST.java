package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.ref.ClassRef;

/**
 * checkcast指令，在pop到引用 obj 之后，又将 obj push 到栈中
 * 类型转换,该指令和instanceof指令的区别在于,instanceof判断后将结果压入操作数栈,而cast直接抛出异常
 */
public class CHECK_CAST extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        OperandStack stack = frame.getOperandStack();
        ClassRef classRef = (ClassRef) cp.getConstant(index).getVal();
        Object object = stack.popRef();
        stack.pushRef(object);
        if (object == null) {
            return;
        }
        if (!object.isInstanceOf(classRef.getClazz())) {
            throw new RuntimeException("类型转换错误");
        }
    }

}
