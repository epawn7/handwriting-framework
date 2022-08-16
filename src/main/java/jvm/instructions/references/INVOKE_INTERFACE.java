package jvm.instructions.references;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.instructions.base.InoviceMethodLogic;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Method;
import jvm.rtda.heap.ref.MethodRef;

public class INVOKE_INTERFACE implements Instruction {

    int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint16();
        reader.readUint8();
        reader.readUint8();
    }

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index).getVal();
        Clazz resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();
        if (resolvedMethod.isStastic() || resolvedMethod.isPrivate()) {
            throw new RuntimeException("编译异常");
        }
        //校验this指针是否为空
        Object othis = frame.getOperandStack().popRefFromTopN(resolvedMethod.getArgSlotCount() - 1);
        if (othis == null) {
            throw new RuntimeException("空指针");
        }
        if (!resolvedClass.isImplements(resolvedMethod.getClazz())) {
            throw new RuntimeException("编译异常");
        }
        Method method = methodRef.lookupMethodInClass(resolvedClass, resolvedMethod.getName(),
                resolvedMethod.getDescriptor());
        if (method == null || method.isAbstract()) {
            throw new RuntimeException("抽象方法异常");
        }
        if (!method.isPublic()) {
            throw new RuntimeException("非法访问");
        }
        InoviceMethodLogic.invokeMethod(frame, method);
    }

}
