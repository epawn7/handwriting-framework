package jvm.instructions.references;

import jvm.instructions.base.Index16Instruction;
import jvm.instructions.base.InoviceMethodLogic;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Method;
import jvm.rtda.heap.StringPool;
import jvm.rtda.heap.ref.MethodRef;

public class INVOKE_VIRTUAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Clazz currentClazz = frame.getMethod().getClazz();
        ConstantPool cp = currentClazz.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index).getVal();
        Clazz resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();
        if (resolvedMethod.isStastic()) {
            throw new RuntimeException("编译错误");
        }
        Object othis = frame.getOperandStack().popRefFromTopN(resolvedMethod.getArgSlotCount() - 1);
        if (othis == null) {
            if (methodRef.getName().equals("println")) {
                //todo println方法
                OperandStack stack = frame.getOperandStack();
                switch (methodRef.getDescriptor()) {
                    case "(Z)V":
                        System.out.println(stack.popInt() != 0);
                        break;
                    case "(C)V":
                        System.out.println(stack.popInt());
                        break;
                    case "(B)V":
                        System.out.println(stack.popInt());
                        break;
                    case "(S)V":
                        System.out.println(stack.popInt());
                        break;
                    case "(I)V":
                        System.out.println(stack.popInt());
                        break;
                    case "(J)V":
                        System.out.println(stack.popLong());
                        break;
                    case "(F)V":
                        System.out.println(stack.popFloat());
                        break;
                    case "(D)V":
                        System.out.println(stack.popDouble());
                        break;
                    case "(Ljava/lang/String;)V":
                        System.out.println(StringPool.getString(stack.popRef()));
                        break;
                    default:
                        System.out.println("println: " + methodRef.getDescriptor());
                }
                stack.popRef();
                return;
            }

            throw new RuntimeException("空指针");
        }
        //验证protected方法
        if (resolvedMethod.isProtected() && resolvedMethod.getClazz().isSubClassOf(currentClazz)
                && !resolvedMethod.getClazz().getPackageName().equals(currentClazz.getPackageName())
                && resolvedMethod.getClazz() != currentClazz && !resolvedClass.isSubClassOf(currentClazz)) {
            throw new RuntimeException("非法访问");
        }

        //多态核心.查找真正对象中的方法,进行调用
        Method method = methodRef.lookupMethodInClass(resolvedClass, methodRef.getName(), methodRef.getDescriptor());
        if (method == null || method.isAbstract()) {
            throw new RuntimeException("调用抽象方法");
        }
        InoviceMethodLogic.invokeMethod(frame, method);
    }

}