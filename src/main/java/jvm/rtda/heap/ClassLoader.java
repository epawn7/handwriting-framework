package jvm.rtda.heap;

import java.util.HashMap;
import java.util.Map;
import jvm.clazz.ClassFile;
import jvm.clazz.ClassReader;
import jvm.entry.ClassPath;
import jvm.instructions.base.ClassNameHelper;
import jvm.rtda.LocalVars;
import jvm.rtda.Object;

/**
 * 类加载器
 */
public class ClassLoader {

    ClassPath classPath;

    ClassReader classReader;

    /**
     * 存放已加载过的类数据
     * key 类的完全限定名
     * value 类数据
     */
    Map<String, Clazz> classMap;

    public ClassLoader(ClassPath classPath, ClassReader classReader) {
        this.classPath = classPath;
        this.classReader = classReader;
        classMap = new HashMap<>();
        loadBasicClass();
        loadPrimitiveClasses();
    }


    private void loadBasicClass() {
        Clazz jclassClazz = loadClass("java/lang/Class");
        classMap.forEach((k, v) -> {
            if (v.getjClass() == null) {
                Object jclass = new Object(jclassClazz);
                v.setjClass(jclass);
                jclass.setExtra(v);
            }
        });
    }

    /**
     * 加载void和基本类型
     */
    private void loadPrimitiveClasses() {
        for (String className : ClassNameHelper.primitiveTypes.keySet()) {
            loadPrimitiveClass(className);
        }
    }

    private void loadPrimitiveClass(String className) {
        Clazz clazz = new Clazz(className, this);
        clazz.jClass = new Object(classMap.get("java/lang/Class"));
        clazz.jClass.setExtra(clazz);
        classMap.put(className, clazz);
    }

    public Clazz loadClass(String name) {
        Clazz clazz = classMap.get(name);
        if (clazz != null) {
            return clazz;
        }
        if (name.startsWith("[")) {
            clazz = loadArrayClass(name);
        } else {
            clazz = loadNoArrayClass(name);
        }

        //为每一个 class 都关联一个元类
        Clazz jclazzClass = classMap.get("java/lang/Class");
        if (jclazzClass != null && clazz.jClass == null) {
            clazz.jClass = new Object(jclazzClass);
            clazz.jClass.setExtra(clazz);
        }
        return clazz;
    }

    public Clazz loadArrayClass(String name) {
        Clazz clazz = new Clazz(name, this);
        classMap.put(name, clazz);
        return clazz;
    }

    public Clazz loadNoArrayClass(String name) {
        //1.读取class文件
        byte[] bytes = classPath.readClass(name);
        //2.解析class文件,放入方法区
        ClassFile classFile = classReader.read(bytes);
        Clazz clazz = new Clazz(classFile);
        clazz.classLoader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        classMap.put(name, clazz);
        //3.进行链接
        verify();
        prepare(clazz);
        return clazz;
    }

    private void prepare(Clazz clazz) {
        calInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    /**
     * 计算实例字段的个数，同时给它们编号
     */
    private void calInstanceFieldSlotIds(Clazz clazz) {
        int slotId = 0;
        if (clazz.supperClass != null) {
            slotId = clazz.supperClass.instanceSlotCount;
        }
        for (Field field : clazz.fields) {
            if (!field.isStastic()) {
                field.slotId = slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount = slotId;
    }

    /**
     * 计算静态字段个数, 同时给它们编号
     */
    private void calcStaticFieldSlotIds(Clazz clazz) {
        int slotId = 0;
        for (Field field : clazz.fields) {
            if (field.isStastic()) {
                field.slotId = slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

    /**
     * 给类变量分配空间，然后给它们赋予初始值
     */
    private void allocAndInitStaticVars(Clazz clazz) {
        LocalVars staticVars = new LocalVars(clazz.staticSlotCount);
        clazz.staticVars = staticVars;
        for (Field field : clazz.fields) {
            if (field.isStastic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }

    private void initStaticFinalVar(Clazz clazz, Field field) {
        int cpIndex = field.constantvalueIndex;
        ConstantPool cp = clazz.constantPool;
        LocalVars staticVars = clazz.staticVars;
        if (cpIndex > 0) {
            switch (field.descriptor) {
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    staticVars.setInt(field.slotId, (int) cp.getConstant(cpIndex).val);
                    break;
                case "J":
                    staticVars.setLong(field.slotId, (long) cp.getConstant(cpIndex).val);
                    break;
                case "F":
                    staticVars.setFloat(field.slotId, (float) cp.getConstant(cpIndex).val);
                    break;
                case "D":
                    staticVars.setDouble(field.slotId, (double) cp.getConstant(cpIndex).val);
                    break;
                case "Ljava/lang/String;":
                    Object strObj = StringPool.newJString(clazz.getClassLoader(), (String) cp.getConstant(cpIndex).val);
                    staticVars.setRef(field.slotId, strObj);
                    break;
            }
        }
    }

    private void verify() {
        //todo 类验证, Java虚拟机规范4.10
    }

    public void resolveSuperClass(Clazz clazz) {
        if (!"java/lang/Object".equals(clazz.name)) {
            clazz.supperClass = loadClass(clazz.superClassName);
        }
    }

    public void resolveInterfaces(Clazz clazz) {
        String[] interfaceNames = clazz.interfaceNames;
        if (interfaceNames != null && interfaceNames.length > 0) {
            clazz.interfaces = new Clazz[interfaceNames.length];
            for (int i = 0; i < interfaceNames.length; i++) {
                clazz.interfaces[i] = loadClass(interfaceNames[i]);
            }
        }
    }

}
