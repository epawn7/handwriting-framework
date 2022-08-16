package jvm.rtda.heap.ref;

import jvm.clazz.constant.ConstantMethodref;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Method;

/**
 * 方法符号引用
 */
public class MethodRef extends MemberRef {

    Method method;

    public MethodRef(ConstantPool constantPool, ConstantMethodref constantMethodref) {
        super(constantPool);
        super.className = constantMethodref.getClassName();
        super.name = constantMethodref.getName();
        super.descriptor = constantMethodref.getDescriptor();
    }

    public Method resolvedMethod() {
        if (method == null) {
            resolvedMethodRef();
        }
        return method;
    }

    public void resolvedMethodRef() {
        Clazz invoicClazz = constantPool.getClazz();
        Clazz methodClazz = resolvedClass();
        if (methodClazz.isInterface()) {
            throw new RuntimeException("类编译错误");
        }
        Method m = lookupMethod(methodClazz, this.name, this.descriptor);
        if (m == null) {
            throw new RuntimeException("方法查找不到");
        }
        if (!m.isAccessibleTo(invoicClazz)) {
            throw new RuntimeException("非法访问");
        }
        this.method = m;
    }

    private Method lookupMethod(Clazz clazz, String methodName, String descriptor) {
        Method m = lookupMethodInClass(clazz, methodName, descriptor);
        if (m == null) {
            m = lookupMethodInInterfaces(clazz.getInterfaces(), methodName, descriptor);
        }
        return m;
    }

    public Method lookupMethodInClass(Clazz clazz, String methodName, String descriptor) {
        for (Clazz c = clazz; c != null; c = c.getSupperClass()) {
            Method[] methods = c.getMethods();
            for (Method m : methods) {
                if (m.getName().equals(methodName) && m.getDescriptor().equals(descriptor)) {
                    return m;
                }
            }
        }
        return null;
    }

    public Method lookupMethodInInterfaces(Clazz[] interfaces, String methodName, String descriptor) {
        for (Clazz iface : interfaces) {
            for (Method m : iface.getMethods()) {
                if (m.getName().equals(methodName) && m.getDescriptor().equals(descriptor)) {
                    return m;
                }
            }
            Method m = lookupMethodInInterfaces(iface.getInterfaces(), methodName, descriptor);
            if (m != null) {
                return m;
            }
        }
        return null;
    }

}
