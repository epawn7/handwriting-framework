package jvm;

import ioc.anno.Bean;
import ioc.anno.Config;
import jvm.command.Command;
import jvm.entry.ClassPath;

@Config
public class ConfigBean {

    @Bean
    public Command command(){
        Command command = new Command();
        command.setClassPath("D:\\software\\java-se-8u41-ri\\jre");
        command.setUserClassPath("D:\\workspace\\study\\handwriting-framework\\target\\classes");
        command.setRight(true);
        command.setClazz("jvm/TestMain3");
        command.setPrintLog(false);
        return command;
    }

    @Bean
    public ClassPath classPath(Command command){
        ClassPath classPath = new ClassPath(command);
        return classPath;
    }
}
