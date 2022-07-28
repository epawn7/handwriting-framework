package util;

public class ByteUtil {


    public static short parseByteToShort(byte[] bytes) {
        if (bytes.length > 2) {
            throw new RuntimeException();
        }
        short a = 0;
        for (int i = 0; i < bytes.length; i++) {
            a = (short) (a << (8 * i) | bytes[i]);
        }
        return a;
    }

    public static int parseByteToInt(byte[] bytes) {
        if (bytes.length > 4) {
            throw new RuntimeException();
        }
        int a = 0;
        for (int i = 0; i < bytes.length; i++) {
            a = bytes[i] << (8 * i) | a;
        }
        return a;
    }

    public static long parseByteToLong(byte[] bytes) {
        long a = 0L;
        for (int i = 0; i < bytes.length; i++) {
            a = (long) bytes[i] << (8 * i) | a;
        }
        return a;
    }

    public static String bytesToHexString(byte[] src, int len) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || len <= 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        byte[] bytes = new byte[4];
        bytes[0] = 2;
        bytes[1] = 2;
        bytes[2] = 2;
        bytes[3] = 2;
        int i = parseByteToInt(bytes);
        System.out.println(i);
        System.out.println(2 << 0 | 0);
        System.out.println(2 << 8 | 2);
        System.out.println(2 << 16);
    }

}
