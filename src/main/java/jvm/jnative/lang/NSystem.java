package jvm.jnative.lang;

import jvm.jnative.Registry;
import jvm.rtda.Frame;
import jvm.rtda.LocalVars;
import jvm.rtda.Object;
import jvm.rtda.heap.Clazz;

public class NSystem {

    static Registry registry = Registry.getInstance();

    static {
        registry.register("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V",
                NSystem::arraycopy);
    }

    public static void arraycopy(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        Object src = localVars.getRef(0);
        int srcPos = localVars.getInt(1);
        Object dest = localVars.getRef(2);
        int destPos = localVars.getInt(3);
        int length = localVars.getInt(4);

        if (src == null || dest == null) {
            throw new RuntimeException("空指针");
        }
        if (!checkArray(src, dest)) {
            throw new RuntimeException("数组储存异常");
        }
        if (srcPos < 0 || destPos < 0 || length < 0 || srcPos + length > src.arrayLength()
                || destPos + length > dest.arrayLength()) {
            throw new RuntimeException("索引越界");
        }

        switch (src.getArrayTypeName()) {
            case "byte[]":
                System.arraycopy(src.getBytes(), srcPos, dest.getBytes(), destPos, length);
                break;
            case "short[]":
                System.arraycopy(src.getShorts(), srcPos, dest.getShorts(), destPos, length);
                break;
            case "char[]":
                System.arraycopy(src.getChars(), srcPos, dest.getChars(), destPos, length);
                break;
            case "int[]":
                System.arraycopy(src.getInts(), srcPos, dest.getInts(), destPos, length);
                break;
            case "long[]":
                System.arraycopy(src.getLongs(), srcPos, dest.getLongs(), destPos, length);
                break;
            case "float[]":
                System.arraycopy(src.getFloats(), srcPos, dest.getFloats(), destPos, length);
                break;
            case "double[]":
                System.arraycopy(src.getDoubles(), srcPos, dest.getDoubles(), destPos, length);
                break;
            case "Object[]":
                System.arraycopy(src.getRefs(), srcPos, dest.getRefs(), destPos, length);
                break;
            default:
                throw new RuntimeException("called length on a none array object!");
        }

    }

    static private boolean checkArray(Object src, Object dest) {
        Clazz srcClazz = src.getClazz();
        Clazz destClazz = dest.getClazz();
        if (!srcClazz.isArray() || !destClazz.isArray()) {
            return false;
        }
        //如果数组是基本类型,则数组的类型必须相同
        if (srcClazz.getComponentClass().isPrimitive() || destClazz.getComponentClass().isPrimitive()) {
            return srcClazz == destClazz;
        }
        return true;
    }

}
