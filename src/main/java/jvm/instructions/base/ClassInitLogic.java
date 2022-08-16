package jvm.instructions.base;

import jvm.rtda.Frame;
import jvm.rtda.Thread;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.Method;

public class ClassInitLogic {

    public static void initClass(Thread thread, Clazz clazz) {
        clazz.startInit();
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }

    private static void scheduleClinit(Thread thread, Clazz clazz) {
        Method clinit = clazz.getClinitMethod();
        if (clinit != null && clinit.getClazz() == clazz) {
            Frame frame = new Frame(thread, clinit);
            thread.pushFrame(frame);
        }
    }

    private static void initSuperClass(Thread thread, Clazz clazz) {
        if (!clazz.isInterface()) {
            Clazz supperClass = clazz.getSupperClass();
            if (supperClass != null && !supperClass.isInitStarted()) {
                initClass(thread, supperClass);
            }
        }
    }

}
