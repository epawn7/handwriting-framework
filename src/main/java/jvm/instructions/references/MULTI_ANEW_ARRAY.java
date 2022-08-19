package jvm.instructions.references;

import java.util.LinkedList;
import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.ref.ClassRef;

/**
 * multianewarray指令创建多维数组
 *
 * 第一个操作数是个uint16索引，通过这个索引可以从运行时常量池中找到一个类符号引用，解析这个引用就可以得到多维数组类。
 * 第二个操作数是个uint8整数，表示数组维度。
 */
public class MULTI_ANEW_ARRAY implements Instruction {

    /**
     * 数组类符号引用
     */
    int index;

    /**
     * 数组维度
     */
    int demensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint16();
        demensions = reader.readUint8();
    }

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index).getVal();
        Clazz arrayClazz = classRef.resolvedClass();
        OperandStack stack = frame.getOperandStack();
        LinkedList<Integer> counts = popAndCheckCounts(stack);
        Object array = newMultiDimensionalArray(counts, arrayClazz);
        stack.pushRef(array);
    }

    private LinkedList<Integer> popAndCheckCounts(OperandStack stack) {
        LinkedList<Integer> counts = new LinkedList<>();
        for (int i = 0; i < demensions; i++) {
            int count = stack.popInt();
            counts.push(count);
            if (count < 0) {
                throw new RuntimeException("数组大小为负数");
            }
        }
        return counts;
    }

    private Object newMultiDimensionalArray(LinkedList<Integer> counts, Clazz arrClass) {
        int count = counts.poll();
        Object array = new Object(arrClass, count);
        if (counts.size() > 0) {
            Object[] refs = array.getRefs();
            for (int i = 0; i < refs.length; i++) {
                refs[i] = newMultiDimensionalArray(counts, arrClass.getComponentClass());
            }
        }
        return array;
    }

}
