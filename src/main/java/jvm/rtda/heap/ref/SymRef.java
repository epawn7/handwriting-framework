package jvm.rtda.heap.ref;

import jvm.rtda.heap.ClassLoader;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;

/**
 * 符号引用
 */
public abstract class SymRef {

    /**
     * 运行时常量池
     */
    ConstantPool constantPool;

    /**
     * 类全限定名
     */
    String className;

    /**
     * 类信息
     */
    Clazz clazz;

    public SymRef(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public Clazz resolvedClass() {
        if (clazz == null) {
            resolveClassInfo();
        }
        return clazz;
    }

    /**
     * 加载类信息
     */
    public void resolveClassInfo() {
        //获取类加载器
        ClassLoader classLoader = constantPool.getClazz().getClassLoader();
        //加载类
        clazz = classLoader.loadClass(className);
        //判断当前类能否访问被加载的类
        if (!clazz.isAccessableTo(constantPool.getClazz())) {
            throw new RuntimeException("类非法访问");
        }
    }

    public Clazz getClazz() {
        return clazz;
    }

}
