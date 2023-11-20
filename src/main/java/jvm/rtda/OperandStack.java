package jvm.rtda;

/**
 * 操作数栈
 */
public class OperandStack {

    /**
     * 初始值为 0,在运行中,代表当前栈顶的 index
     */
    int size;

    Slot[] slots;

    public OperandStack(int maxStack) {
        size = 0;
        slots = new Slot[maxStack];
    }

    public Object popRefFromTopN(int n) {
        if (n > size) {
            return null;
        }
        return slots[size - n - 1].object;
    }

    public void pushSlot(Slot slot) {
        slots[size++] = slot;
    }

    public Slot popSlot() {
        return slots[--size];
    }


    public void pushInt(int val) {
        Slot s = new Slot();
        s.num = val;
        slots[size++] = s;
    }

    public int popInt() {
        Slot slot = slots[--size];
        slots[size] = null;
        return slot.num;
    }

    public void pushBool(boolean val) {
        if (val) {
            pushInt(1);
        } else {
            pushInt(0);
        }
    }

    public boolean popBool() {
        return popInt() != 0;
    }

    public void pushFloat(float val) {
        Slot s = new Slot();
        s.num = Float.floatToIntBits(val);
        slots[size++] = s;
    }

    public float popFloat() {
        Slot slot = slots[--size];
        slots[size] = null;
        return Float.intBitsToFloat(slot.num);
    }

    public void pushLong(long val) {
        //先存低 32 位
        Slot s1 = new Slot();
        s1.num = (int) val;
        slots[size++] = s1;
        //再存高 32 位
        Slot s2 = new Slot();
        s2.num = (int) (val >> 32);
        slots[size++] = s2;
    }

    public long popLong() {
        long high = slots[--size].num;
        slots[size] = null;
        int low = slots[--size].num;
        slots[size] = null;
        return high << 32 | low;
    }

    public void pushDouble(double val) {
        pushLong(Double.doubleToLongBits(val));
    }

    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }

    public void pushRef(Object object) {
        Slot s = new Slot();
        s.object = object;
        slots[size++] = s;
    }

    public Object popRef() {
        Slot slot = slots[--size];
        slots[size] = null;
        return slot.object;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            slots[i] = null;
        }
        size = 0;

    }

}
