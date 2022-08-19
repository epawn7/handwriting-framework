package jvm.instructions.references;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.instructions.base.ArrayTypeConst;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.ClassLoader;
import jvm.rtda.heap.Clazz;

/**
 * 创建数组
 *
 * newarray指令需要两个操作数。第一个操作数是一个uint8整数，在字节码中紧跟在指令操作码后面，表示要创建哪种类型的数 组。
 * newarray指令的第二个操作数是count，从操作数栈中弹出，表示数组长度。
 */
public class NEW_ARRAY implements Instruction {

    /**
     * uint8整数
     *
     * @see ArrayTypeConst
     */
    int atype;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.atype = reader.readUint8();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new RuntimeException("数组大小小于0");
        }
        Clazz arrayClazz = getArrayClazz(atype, frame.getMethod().getClazz().getClassLoader());
        Object array = new Object(arrayClazz, count);
        stack.pushRef(array);
    }

    private Clazz getArrayClazz(int atype, ClassLoader classLoader) {
        switch (atype) {
            case ArrayTypeConst.AT_BOOLEAN:
                return classLoader.loadArrayClass("[Z");
            case ArrayTypeConst.AT_BYTE:
                return classLoader.loadArrayClass("[B");
            case ArrayTypeConst.AT_CHAR:
                return classLoader.loadArrayClass("[C");
            case ArrayTypeConst.AT_SHORT:
                return classLoader.loadArrayClass("[S");
            case ArrayTypeConst.AT_INT:
                return classLoader.loadArrayClass("[I");
            case ArrayTypeConst.AT_LONG:
                return classLoader.loadArrayClass("[J");
            case ArrayTypeConst.AT_FLOAT:
                return classLoader.loadArrayClass("[F");
            case ArrayTypeConst.AT_DOUBLE:
                return classLoader.loadArrayClass("[D");
            default:
                throw new RuntimeException("无效atype");
        }
    }

}
