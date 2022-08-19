package jvm.instructions.references;

import jvm.instructions.base.ClassInitLogic;
import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.LocalVars;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Field;
import jvm.rtda.heap.ref.FieldRef;

/**
 * 取出类的某个静态变量值，然后推入栈顶
 */
public class GET_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //获取字段引用
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(this.index).getVal();
        Field field = fieldRef.resolvedField();
        Clazz clazz = field.getClazz();
        //判断类是否已经初始化
        if (!clazz.isInitStarted()) {
            //类还未初始化,退回当前栈帧的pc,用于后面重新执行
            frame.revertNextPc();
            //调用类初始化方法
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }
        LocalVars staticVars = clazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();

        //字段校验
        if (!field.isStastic()) {
            throw new RuntimeException("类编译异常");
        }

        switch (field.getDescriptor().charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(staticVars.getInt(field.getSlotId()));
                break;
            case 'F':
                stack.pushFloat(staticVars.getFloat(field.getSlotId()));
                break;
            case 'J':
                stack.pushLong(staticVars.getLong(field.getSlotId()));
                break;
            case 'D':
                stack.pushDouble(staticVars.getDouble(field.getSlotId()));
                break;
            case 'L':
            case '[':
                stack.pushRef(staticVars.getRef(field.getSlotId()));
                break;
        }
    }

}
