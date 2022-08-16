package jvm.instructions.references;

import jvm.instructions.base.ClassInitLogic;
import jvm.instructions.base.Index16Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.ref.ClassRef;

/**
 * 专门用来创建类实例
 */
public class NEW extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        //获取类引用
        ClassRef classRef = (ClassRef) cp.getConstant(this.index).getVal();
        //解析类引用,获取类信息
        Clazz clazz = classRef.resolvedClass();

        //判断类是否已经初始化
        if (!clazz.isInitStarted()) {
            //类还未初始化,退回当前栈帧的pc,用于后面重新执行
            frame.revertNextPc();
            //调用类初始化方法
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }
        if (clazz.isInterface() || clazz.isAbstract()) {
            throw new RuntimeException("非法访问");
        }
        //创建对象
        Object ref = new Object(clazz);
        //将对象引用放到操作栈中
        frame.getOperandStack().pushRef(ref);
    }

}
