package jvm.rtda.heap.ref;

import jvm.clazz.constant.ConstantClass;
import jvm.rtda.heap.ConstantPool;

/**
 * 类引用
 */
public class ClassRef extends SymRef {

    public ClassRef(ConstantPool constantPool, ConstantClass constantClass) {
        super(constantPool);
        super.className = constantClass.getClassName();
    }

}
