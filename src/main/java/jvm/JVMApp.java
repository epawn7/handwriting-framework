package jvm;

import ioc.IocContainer;
import jvm.command.Command;
import jvm.instructions.Interpreter;
import jvm.rtda.heap.ClassLoader;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.Method;

public class JVMApp {

    public static void main(String[] args) {
        IocContainer container = IocContainer.getInstance();
        container.scan(JVMApp.class);
        container.init();
        Command command = container.getBean(Command.class);
        ClassLoader classLoader = container.getBean(ClassLoader.class);
        Clazz clazz = classLoader.loadClass(command.getClazz());
        Method mainMethod = getMainMethod(clazz);
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(mainMethod, true);
    }

    private static Method getMainMethod(Clazz clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isStastic() && method.getName().equals("main") && method.getDescriptor()
                    .equals("([Ljava/lang/String;)V")) {
                return method;
            }
        }
        return null;
    }

}
