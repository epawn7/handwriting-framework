package jvm.rtda.heap;

import jvm.clazz.ClassFile;
import jvm.clazz.constant.ConstantClass;
import jvm.clazz.constant.ConstantUtf8;
import jvm.rtda.LocalVars;

/**
 * 存放class相关信息
 */
public class Clazz {

    /**
     * 访问标记
     */
    int accessFlags;

    /**
     * 类名称,完全限定名
     * java/lang/Object
     */
    String name;

    /**
     * 父类名称
     */
    String superClassName;

    /**
     * 运行时常量池
     */
    ConstantPool constantPool;

    /**
     * 字段列表
     */
    Field[] fields;

    /**
     * 方法列表
     */
    Method[] methods;

    /**
     * 类加载器
     */
    ClassLoader classLoader;

    /**
     * 父类引用
     */
    Clazz supperClass;

    /**
     * 接口引用列表
     */
    Clazz[] interfaces;

    /**
     * 接口名称
     */
    String[] interfaceNames;

    /**
     * 实例变量数量
     */
    int instanceSlotCount;

    /**
     * 静态变量数量
     */
    int staticSlotCount;

    /**
     * 静态变量数据存放地方
     */
    LocalVars staticVars;

    /**
     * 包名
     */
    String packageName;

    /**
     * 是否完成初始化
     */
    boolean initStarted;

    public Clazz(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlag();
        this.name = classFile.getClassName();
        this.superClassName = classFile.getSuperClassName();
        if (classFile.getInterfaceCount() > 0) {
            interfaceNames = new String[classFile.getInterfaceCount()];
            for (int i = 0; i < classFile.getInterfaces().length; i++) {
                ConstantClass constantClass = (ConstantClass) classFile.getConstantPool()[classFile.getInterfaces()[i]];
                ConstantUtf8 constantUtf8 = (ConstantUtf8) classFile.getConstantPool()[constantClass.getNameIndex()];
                interfaceNames[i] = constantUtf8.getValue();
            }
        }
        this.constantPool = new ConstantPool(this, classFile.getConstantPool());
        fields = Field.newFields(this, classFile.getFields(), classFile.getConstantPool());
        methods = Method.newMethods(this, classFile.getMethods(), classFile.getConstantPool());
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getPackageName() {
        if (name.indexOf('/') > 0) {
            return name.substring(0, name.lastIndexOf('/'));
        }
        return "";
    }

    public boolean isPublic() {
        return (this.accessFlags & AccessFlagConst.ACC_PUBLIC) != 0;
    }

    public boolean isSuper() {
        return 0 != (accessFlags & AccessFlagConst.ACC_SUPER);
    }

    /**
     * 当前类是否是parent的子类
     */
    public boolean isSubClassOf(Clazz parent) {
        for (Clazz c = supperClass; c != null; c = c.supperClass) {
            if (c == parent) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查看当前类是否是sub的父类
     */
    public boolean isSuperClassOf(Clazz sub) {
        return sub.isSubClassOf(this);
    }

    public boolean isImplements(Clazz iface) {
        for (Clazz c = this; c != null; c = c.supperClass) {
            for (int i = 0; i < c.interfaces.length; i++) {
                if (c.interfaces[i] == iface || c.interfaces[i].isSubInterfaceOf(iface)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSubInterfaceOf(Clazz iface) {
        for (Clazz superInterface : interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInterface() {
        return 0 != (accessFlags & AccessFlagConst.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (accessFlags & AccessFlagConst.ACC_ABSTRACT);
    }

    public boolean isAccessableTo(Clazz clazz) {
        if (this.isPublic() || this.getPackageName().equals(clazz.getPackageName())) {
            return true;
        }
        return false;
    }

    public Field[] getFields() {
        return fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void startInit() {
        this.initStarted = true;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public Clazz getSupperClass() {
        return supperClass;
    }

    public Clazz[] getInterfaces() {
        return interfaces;
    }

    public String getName() {
        return name;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public LocalVars getStaticVars() {
        return staticVars;
    }

    public boolean isAssignableFrom(Clazz clazz) {
        if (clazz == this) {
            return true;
        }
        if (this.isInterface()) {
            clazz.isSubClassOf(this);
        } else {
            clazz.isImplements(this);
        }
        return false;
    }


    public Method getClinitMethod() {
        for (Method method : methods) {
            if (method.name.equals("<clinit>") && method.descriptor.equals("()V")) {
                return method;
            }
        }
        return null;
    }

}
