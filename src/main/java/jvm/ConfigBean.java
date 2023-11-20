package jvm;

import ioc.anno.Bean;
import ioc.anno.Config;
import jvm.clazz.ClassReader;
import jvm.command.Command;
import jvm.entry.ClassPath;
import jvm.rtda.heap.ClassLoader;

@Config
public class ConfigBean {

    @Bean
    public Command command(){
        Command command = new Command();
        command.setClassPath("D:\\workspace\\jdk\\openjdk-8u342-b07\\jre");
        command.setUserClassPath("D:\\workspace\\study\\handwriting-framework\\target\\classes");
        command.setRight(true);
        command.setClazz("jvm/TestMain6");
        command.setPrintLog(false);
        return command;
    }

    @Bean
    public ClassPath classPath(Command command) {
        ClassPath classPath = new ClassPath(command);
        return classPath;
    }

    @Bean
    public ClassLoader classLoader(ClassPath classPath, ClassReader classReader) {
        return new ClassLoader(classPath, classReader);
    }

}
