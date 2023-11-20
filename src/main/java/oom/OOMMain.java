package oom;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Random;

public class OOMMain {

    static Random random = new Random();

    public static void main(String[] args) {
        LinkedList<Byte[]> data = new LinkedList<>();
        int i = 0;
        try {
            for (i = 0; ; i++) {
                if (pushData(data.size())) {
                    data.push(new Byte[1024 * 1024]);
                }
                if (popData(data.size())) {
                    if (data.size() % 2 == 0) {
                        data.poll();
                    } else {
                        data.pop();
                    }
                }
            }
        } catch (Throwable e) {
            System.out.println("循环次数:" + i);
            throw e;
        } finally {
            System.out.println("循环次数:" + i);
        }

    }

    public static boolean pushData(int listSize) {
        if (listSize == 0) {
            return true;
        }
        int i = random.nextInt(10);
        int nlognx = (int) nlognx(2, listSize);
        return nlognx >= i;
    }

    public static boolean popData(int listSize) {
        if (listSize == 0) {
            return false;
        }
        int i = random.nextInt(10);
        int lognx = (int) lognx(2, listSize) / 2;
        return lognx >= i;
    }


    private static double lognx(double n, double x) {

        BigDecimal a = BigDecimal.ONE;

        return Math.log(x + 1) / Math.log(n);
    }

    private static double nlognx(double n, double x) {
        return -(Math.log(x + 1) / Math.log(n)) + 10;
    }

}
