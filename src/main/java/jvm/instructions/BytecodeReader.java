package jvm.instructions;

public class BytecodeReader {

    byte[] code;

    int pc;


    public void reset(byte[] code, int pc) {
        this.code = code;
        this.pc = pc;
    }

    public int readUint8() {
        return code[pc++] & 0xFF;
    }

    public byte readByte() {
        return code[pc++];
    }

    public int readUint16() {
        int b1 = readUint8();
        int b2 = readUint8();
        return (b1 << 8 | b2);
    }

    public short readShort() {
        byte b1 = code[pc++];
        byte b2 = code[pc++];
        return (short) ((b1 << 8) | b2);
    }

    public int readInt() {

        return 0;
    }

    //4k对齐,没有对齐的会有填充数据,这些数据要忽略掉;
    public void skipPadding() {
        while (pc % 4 != 0) {
            readByte();
        }
    }

    public int[] readInts(int count) {
        int[] vals = new int[count];
        for (int i = 0; i < count; i++) {
            vals[i] = readInt();
        }
        return vals;
    }

}
