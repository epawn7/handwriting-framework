package jvm.instructions.references;

import jvm.instructions.base.ClassInitLogic;
import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.LocalVars;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Field;
import jvm.rtda.heap.Method;
import jvm.rtda.heap.ref.FieldRef;
import util.Log;

/**
 * 类的某个静态变量赋值
 *
 * 需要两个操作数。
 * 第一个操作数是uint16索引，来自字节码。
 * 第二个操作数是要赋给静 态变量的值，从操作数栈中弹出
 */
public class PUT_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        //获取字段引用
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldref = (FieldRef) cp.getConstant(this.index).getVal();
        Field field = fieldref.resolvedField();

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
        if (field.isFinal()) {
            Method method = frame.getMethod();
            if (method.getClazz() != field.getClazz() || !method.getName().equals("<clinit>")) {
                throw new RuntimeException("非法访问");
            }
        }
        //从操作栈中获取数据,放入到静态表中
        Log.debug("pus_static,field.getDescriptor:{}", field.getDescriptor());
        switch (field.getDescriptor().charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                staticVars.setInt(field.getSlotId(), stack.popInt());
                break;
            case 'F':
                staticVars.setFloat(field.getSlotId(), stack.popFloat());
                break;
            case 'J':
                staticVars.setLong(field.getSlotId(), stack.popLong());
                break;
            case 'D':
                staticVars.setDouble(field.getSlotId(), stack.popDouble());
                break;
            case 'L':
            case '[':
                staticVars.setRef(field.getSlotId(), stack.popRef());
                break;
            default:
                break;
        }
    }

}
