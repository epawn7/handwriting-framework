package jvm.rtda;

import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.Field;

/**
 * 对象
 */
public class Object {

    /**
     * 类
     */
    Clazz clazz;

    /**
     * 静态字段
     */
    java.lang.Object data;

    /**
     * 创建对象
     *
     * @param clazz 对象的类
     */
    public Object(Clazz clazz) {
        this.clazz = clazz;
        data = new LocalVars(clazz.getInstanceSlotCount());
    }

    /**
     * 创建数组对象
     *
     * @param clazz 数组类
     * @param count 数组大小
     */
    public Object(Clazz clazz, int count) {
        if (!clazz.isArray()) {
            throw new RuntimeException("类不是数组类型");
        }
        this.clazz = clazz;
        switch (clazz.getName()) {
            case "[Z":
                data = new byte[count];
                break;
            case "[B":
                data = new boolean[count];
                break;
            case "[C":
                data = new char[count];
                break;
            case "[S":
                data = new short[count];
                break;
            case "[I":
                data = new int[count];
                break;
            case "[J":
                data = new long[count];
                break;
            case "[F":
                data = new float[count];
                break;
            case "[D":
                data = new double[count];
                break;
            default:
                data = new Object[count];
                break;
        }
    }

    /**
     * 创建对象
     *
     * @param clazz 类名
     * @param data 数据
     */
    public Object(Clazz clazz, java.lang.Object data) {
        this.clazz = clazz;
        this.data = data;
    }

    public LocalVars getFields() {
        return (LocalVars) data;
    }

    public void setRefVar(String fieldName, String fieldDescriptor, Object value) {
        Field field = clazz.getField(fieldName, fieldDescriptor);
        getFields().setRef(field.getSlotId(), value);
    }

    public Clazz getClazz() {
        return clazz;
    }

    public boolean isInstanceOf(Clazz clazz) {
        return clazz.isAssignableFrom(this.getClazz());
    }


    //---数组特有方法----
    public byte[] getBytes() {
        return (byte[]) data;
    }

    public char[] getChars() {
        return (char[]) data;
    }

    public short[] getShorts() {
        return (short[]) data;
    }

    public int[] getInts() {
        return (int[]) data;
    }

    public long[] getLongs() {
        return (long[]) data;
    }

    public float[] getFloats() {
        return (float[]) data;
    }

    public double[] getDoubles() {
        return (double[]) data;
    }

    public Object[] getRefs() {
        return (Object[]) data;
    }

    //---数组特有方法----

    public int arrayLength() {
        switch (data.getClass().getSimpleName()) {
            case "byte[]":
                return getBytes().length;
            case "short[]":
                return getShorts().length;
            case "char[]":
                return getChars().length;
            case "int[]":
                return getInts().length;
            case "long[]":
                return getLongs().length;
            case "float[]":
                return getFloats().length;
            case "double[]":
                return getDoubles().length;
            case "Object[]":
                return getRefs().length;
            default:
                throw new RuntimeException("called length on a none array object!");
        }
    }

    public Object getRefVar(String fieldName, String fieldDescriptor) {
        Field field = clazz.getField(fieldName, fieldDescriptor);
        return getFields().getRef(field.getSlotId());
    }

}
