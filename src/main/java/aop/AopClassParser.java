package aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import aop.anno.Aspect;
import scanner.ClassParser;

public class AopClassParser implements ClassParser {


    private List<InterceptMatch> matchList = new ArrayList<>();

    private AopBeanProcessHandler processHandler;

    public AopClassParser(AopBeanProcessHandler processHandler) {
        this.processHandler = processHandler;
    }

    @Override
    public boolean matchClass(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Aspect.class)) {
            return false;
        }
        if (InterceptMatch.class.isAssignableFrom(clazz)) {
            try {
                Object o = clazz.newInstance();
                matchList.add((InterceptMatch) o);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean matchMethod(Class clazz, Method method, Parameter[] parameters) {
        return false;
    }

    @Override
    public boolean matchField(Class clazz, Field field) {
        return false;
    }

    @Override
    public boolean scanClassOver(Class clazz) {
        return false;
    }

    @Override
    public boolean scanAllOver() {
        processHandler.setMatchList(matchList);
        return true;
    }

}
