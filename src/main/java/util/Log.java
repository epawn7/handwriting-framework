package util;

public class Log {

    public static void debug(String msg) {
        log(msg, null);
    }


    public static void debug(String msg, Throwable e) {
        log(msg, e);
    }

    public static void debug(String msg, Throwable e, Object... args) {
        log(msg, e, args);
    }

    public static void debug(String msg, Object... args) {
        log(msg, null, args);
    }

    private static void log(String msg, Throwable e, Object... args) {
        StringBuilder builder = new StringBuilder(msg);
        for (Object arg : args) {
            int i = builder.indexOf("{}");
            builder.replace(i, i + 1, arg.toString());
        }
        System.out.println(builder);
        if (e != null) {
            e.printStackTrace();
        }
    }

}
