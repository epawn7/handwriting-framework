package jvm.rtda.heap.ref;

import jvm.clazz.constant.ConstantInterfaceMethodref;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Method;

/**
 * 接口方法符号引用
 */
public class InterfaceMethodRef extends MemberRef {

    Method method;

    public InterfaceMethodRef(ConstantPool constantPool, ConstantInterfaceMethodref constantInterfaceMethodref) {
        super(constantPool);
        super.className = constantInterfaceMethodref.getClassName();
        super.name = constantInterfaceMethodref.getName();
        super.descriptor = constantInterfaceMethodref.getDescriptor();
    }

    public Method resolvedInterfaceMethod() {
        if (method == null) {
            resolvedInterfaceMethodRef();
        }
        return method;
    }

    public void resolvedInterfaceMethodRef() {
        Clazz invoicClazz = constantPool.getClazz();
        Clazz methodClazz = resolvedClass();
        if (!methodClazz.isInterface()) {
            throw new RuntimeException("类编译错误");
        }
        Method m = lookupInterfaceMethod(methodClazz, this.name, this.descriptor);
        if (m == null) {
            throw new RuntimeException("方法查找不到");
        }
        if (!m.isAccessibleTo(invoicClazz)) {
            throw new RuntimeException("非法访问");
        }
        this.method = m;
    }

    private Method lookupInterfaceMethod(Clazz clazz, String methodName, String descriptor) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(methodName) && m.getDescriptor().equals(descriptor)) {
                return m;
            }
        }
        return lookupMethodInInterfaces(clazz.getInterfaces(), methodName, descriptor);
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
