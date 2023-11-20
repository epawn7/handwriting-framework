package jvm.rtda.heap;

import jvm.clazz.attribute.CodeAttribute;
import jvm.rtda.heap.ref.ClassRef;

public class ExceptionTable {

    ExceptionHandler[] exceptionHandlers;

    public ExceptionTable(CodeAttribute.ExceptionTable[] tables, ConstantPool cp) {
        exceptionHandlers = new ExceptionHandler[tables.length];
        for (int i = 0; i < tables.length; i++) {
            CodeAttribute.ExceptionTable table = tables[i];
            ExceptionHandler handler = new ExceptionHandler();
            handler.startPc = table.getStartPc();
            handler.endPc = table.getEndPc();
            handler.handlerPc = table.getHandlerPc();
            short catchType = table.getCatchType();
            if (catchType != 0) {
                handler.catchType = (ClassRef) cp.getConstant(catchType).getVal();
            }
            exceptionHandlers[i] = handler;
        }
    }

    public ExceptionHandler findExceptionHandler(int pc, Clazz expClass) {
        for (ExceptionHandler handler : exceptionHandlers) {
            if (handler.startPc < pc && pc < handler.endPc) {
                if (handler.catchType == null) {
                    return handler;
                }
                Clazz catchClass = handler.catchType.resolvedClass();
                if (catchClass == expClass || catchClass.isSuperClassOf(expClass)) {
                    return handler;
                }
            }
        }
        return null;
    }

    public static class ExceptionHandler {

        int startPc;

        int endPc;

        int handlerPc;

        ClassRef catchType;

    }

}
