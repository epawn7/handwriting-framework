package jvm;

import ioc.IocContainer;
import jvm.clazz.ClassReader;
import jvm.command.Command;
import jvm.entry.ClassPath;

public class JVMApp {

    public static void main(String[] args) {
        IocContainer container = IocContainer.getInstance();
        container.scan(JVMApp.class);
        container.init();
        ClassPath classPath = container.getBean(ClassPath.class);
        Command command = container.getBean(Command.class);
        byte[] bytes = classPath.readClass(command.getClazz());
        ClassReader classReader = container.getBean(ClassReader.class);
        classReader.read(bytes);
        System.out.println(bytes.length);
    }

    public static Command createCommand() {
        Command command = new Command();
        command.setRight(true);
        command.setClassPath("");
        return command;
    }

}
