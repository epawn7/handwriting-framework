package jvm.command;

/**
 2022-07-20
 */
public class Command {
    private String[] args;

    private Boolean isRight;

    private String classPath;

    private String clazz;

    private String userClassPath;

    private String[] options;

    public String getUserClassPath() {
        return userClassPath;
    }

    public void setUserClassPath(String userClassPath) {
        this.userClassPath = userClassPath;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

}
