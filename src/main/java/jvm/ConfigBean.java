package jvm;

import ioc.anno.Bean;
import ioc.anno.Config;
import jvm.command.Command;
import jvm.entry.ClassPath;

/**
 * @author jinfan 2022-07-22
 */
@Config
public class ConfigBean {

    @Bean
    public Command command(){
        Command command = new Command();
        command.setClassPath("D:\\software\\java-se-8u41-ri\\jre");
        command.setRight(true);
        command.setClazz("java/lang/Object.class");
        return command;
    }

    @Bean
    public ClassPath classPath(Command command){
        ClassPath classPath = new ClassPath(command);

        return classPath;
    }
}
