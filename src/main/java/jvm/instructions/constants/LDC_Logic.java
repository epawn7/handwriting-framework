package jvm.instructions.constants;

import jvm.clazz.ConstantTagEnum;
import jvm.rtda.Frame;
import jvm.rtda.Object;
import jvm.rtda.OperandStack;
import jvm.rtda.heap.Constant;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.StringPool;

public class LDC_Logic {

    public static void _ldc(Frame frame, int index) {
        OperandStack stack = frame.getOperandStack();
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        Constant constant = cp.getConstant(index);
        ConstantTagEnum tagEnum = ConstantTagEnum.of((byte) constant.getType());
        switch (tagEnum) {
            case CONSTANT_Integer_info:
                stack.pushInt((Integer) constant.getVal());
                break;
            case CONSTANT_Float_info:
                stack.pushFloat((Float) constant.getVal());
                break;
            case CONSTANT_String_info:
                Object strObj = StringPool.newJString(frame.getMethod().getClazz().getClassLoader(),
                        (String) constant.getVal());
                stack.pushRef(strObj);
                break;
//            case CONSTANT_Class_info:
//                break;
            default:
                throw new RuntimeException("todo: ldc");
        }
    }

}
