package jvm.rtda.heap;

import jvm.clazz.ClassFile;
import jvm.clazz.constant.ConstantClass;
import jvm.clazz.constant.ConstantUtf8;
import jvm.instructions.base.ClassNameHelper;
import jvm.rtda.LocalVars;
import jvm.rtda.Object;

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

    /**
     * 当前类属性对象
     */
    Object jClass;

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

    public Clazz(String name, ClassLoader classLoader) {
        this.accessFlags = AccessFlagConst.ACC_PUBLIC;
        this.name = name;
        this.classLoader = classLoader;
        this.initStarted = true;
        this.supperClass = classLoader.loadClass("java/lang/Object");
        this.interfaces = new Clazz[]{classLoader.loadClass("java/lang/Cloneable"), classLoader.loadClass("java/io/Serializable")};
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

    /**
     * 获取java名称
     */
    public String getJavaName() {
        return name.replace("/", ".");
    }

    public String getArrayName() {
        return ClassNameHelper.getArrayClassName(this.getName());
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

    public boolean isArray() {
        return name.startsWith("[");
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

    public Field getField(String fieldName, String fieldDescriptor) {
        for (Clazz c = this; c != null; c = c.supperClass) {
            for (Field field : c.getFields()) {
                if (field.getName().equals(fieldName) && field.getDescriptor().equals(fieldDescriptor)) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 当前类是否是source的子类
     */
    public boolean isAssignableFrom(Clazz source) {
        if (source == this) {
            return true;
        }
        if (source.isArray()) {
            if (this.isArray()) {
                Clazz c1 = source.getComponentClass();
                Clazz c2 = this.getComponentClass();
                return c1 == c2 || c2.isAssignableFrom(c1);
            } else {
                if (this.isInterface()) {
                    return this.isJlCloneable() || this.isJioSerializable();
                } else {
                    return this.isJlObject();
                }
            }
        } else {
            if (source.isInterface()) {
                if (this.isInterface()) {
                    //source和this都是接口
                    return this.isSuperInterfaceOf(source);
                } else {
                    //当source为接口,this为类时,只有Object类才能为父类
                    return this.isJlObject();
                }
            } else {
                if (this.isInterface()) {
                    //source为类,this为接口
                    return source.isImplements(this);
                } else {
                    //source和this都为类
                    return source.isSubClassOf(this);
                }
            }
        }
    }

    public boolean isSuperInterfaceOf(Clazz source) {
        return source.isSubInterfaceOf(this);
    }

    /**
     * 判断当前类是否是Object
     */
    public boolean isJlObject() {
        return "java/lang/Object".equals(name);
    }

    public boolean isJlCloneable() {
        return "java/lang/Cloneable".equals(name);
    }

    public boolean isJioSerializable() {
        return "java/io/Serializable".equals(name);
    }

    public Method getClinitMethod() {
        for (Method method : methods) {
            if (method.name.equals("<clinit>") && method.descriptor.equals("()V")) {
                return method;
            }
        }
        return null;
    }

    public Clazz getComponentClass() {
        String componentClassName = ClassNameHelper.getComponentClassName(this.name);
        return classLoader.loadClass(componentClassName);
    }


    public Object getjClass() {
        return jClass;
    }

    public void setjClass(Object jClass) {
        this.jClass = jClass;
    }

    /**
     * 判断是否是基本类型
     */
    public boolean isPrimitive() {
        return ClassNameHelper.primitiveTypes.containsKey(this.name);
    }

}
