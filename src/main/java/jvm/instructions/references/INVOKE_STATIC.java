package jvm.instructions.references;

import jvm.instructions.base.ClassInitLogic;
import jvm.instructions.base.Index16Instruction;
import jvm.instructions.base.InoviceMethodLogic;
import jvm.rtda.Frame;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Method;
import jvm.rtda.heap.ref.MethodRef;

/**
 * 调用static方法
 */
public class INVOKE_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index).getVal();
        Method method = methodRef.resolvedMethod();
        Clazz clazz = methodRef.getClazz();
        //判断类是否已经初始化
        if (!clazz.isInitStarted()) {
            //类还未初始化,退回当前栈帧的pc,用于后面重新执行
            frame.revertNextPc();
            //调用类初始化方法
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }
        if (!method.isStastic()) {
            throw new RuntimeException("编译异常");
        }
        InoviceMethodLogic.invokeMethod(frame, method);
    }

}
