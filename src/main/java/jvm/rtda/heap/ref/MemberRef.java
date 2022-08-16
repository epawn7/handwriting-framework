package jvm.rtda.heap.ref;

import jvm.rtda.heap.ConstantPool;

/**
 * 存放方法和字段符号引用共有信息
 */
public abstract class MemberRef extends SymRef {

    String name;

    String descriptor;

    public MemberRef(ConstantPool constantPool) {
        super(constantPool);
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

}
