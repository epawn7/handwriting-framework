package jvm.rtda;

/**
 * 2022-08-01
 */
public class LocalVars {

    /**
     * 局部变量表,JVM 规定其按照索引访问,所以将其设置为数组
     */
    Slot[] localVars;

    public LocalVars(int maxLocals) {
        localVars = new Slot[maxLocals];
    }

    public void setSlot(int index, Slot slot) {
        localVars[index] = slot;
    }

    public void setInt(int index, int val) {
        Slot s = new Slot();
        s.num = val;
        localVars[index] = s;
    }

    public void setFloat(int index, float val) {
        setInt(index, Float.floatToIntBits(val));
    }

    public void setLong(int index, long val) {
        //先存低 32 位
        Slot s1 = new Slot();
        s1.num = (int) val;
        localVars[index] = s1;
        //再存高 32 位
        Slot s2 = new Slot();
        s2.num = (int) (val >> 32);
        localVars[index + 1] = s2;
    }

    public void setDouble(int index, double val) {
        long tempVal = Double.doubleToLongBits(val);
        setLong(index, tempVal);
    }

    public int getInt(int index) {
        if (localVars[index] == null) {
            return 0;
        }
        return localVars[index].num;
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(localVars[index].num);
    }

    public long getLong(int index) {
        int low = localVars[index].num;
        long high = localVars[index + 1].num;
        return ((high & 0x000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    public Object getRef(int index) {
        if (localVars[index] == null) {
            return null;
        }
        return localVars[index].object;
    }

    public void setRef(int index, Object object) {
        Slot s = new Slot();
        s.object = object;
        localVars[index] = s;
    }

}
