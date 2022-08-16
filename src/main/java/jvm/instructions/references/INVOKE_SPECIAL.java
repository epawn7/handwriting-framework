package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.instructions.base.InoviceMethodLogic;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Method;
import jvm.rtda.heap.ref.MethodRef;

/**
 * 调用实例中的方法
 */
public class INVOKE_SPECIAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Clazz currentClazz = frame.getMethod().getClazz();
        ConstantPool cp = currentClazz.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index).getVal();
        Clazz resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();
        if (resolvedMethod.getName().equals("<init>") && resolvedMethod.getClazz() != resolvedClass) {
            throw new RuntimeException("没有找到方法");
        }
        if (resolvedMethod.isStastic()) {
            throw new RuntimeException("编译错误");
        }
        //校验this指针是否为空
        Object othis = frame.getOperandStack().popRefFromTopN(resolvedMethod.getArgSlotCount() - 1);
        if (othis == null) {
            throw new RuntimeException("空指针");
        }
        //验证protected方法
        if (resolvedMethod.isProtected() && resolvedMethod.getClazz().isSubClassOf(currentClazz)
                && !resolvedMethod.getClazz().getPackageName().equals(currentClazz.getPackageName())
                && resolvedMethod.getClazz() != currentClazz && !resolvedClass.isSubClassOf(currentClazz)) {
            throw new RuntimeException("非法访问");
        }
        Method methodtoBeInvoked = resolvedMethod;
        //ACC_SUPER标志一直为true
        //如果 调用的中超类中的函数，但不是构造函数，且当前类的ACC_SUPER标志被设置，需要一个额外的过程查找最终要调用的方法；
        //否则前面从方法符号引用中解析出来的方法就是要调用的方法
        if (resolvedClass.isSuper() && resolvedClass.isSuperClassOf(currentClazz) && !"<init>".equals(
                resolvedMethod.getName())) {
            methodtoBeInvoked = methodRef.lookupMethodInClass(currentClazz.getSupperClass(), methodRef.getName(),
                    methodRef.getDescriptor());
        }
        if (methodtoBeInvoked == null || methodtoBeInvoked.isAbstract()) {
            throw new RuntimeException("抽象类为实现");
        }
        InoviceMethodLogic.invokeMethod(frame, methodtoBeInvoked);
    }

}
