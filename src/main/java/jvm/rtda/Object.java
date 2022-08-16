package jvm.rtda;

import jvm.rtda.heap.Clazz;

/**
 * 对象
 */
public class Object {

    /**
     * 类
     */
    Clazz clazz;

    /**
     * 静态字段
     */
    LocalVars fields;

    public Object(Clazz clazz) {
        this.clazz = clazz;
        fields = new LocalVars(clazz.getInstanceSlotCount());
    }

    public LocalVars getFields() {
        return fields;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public boolean isInstanceOf(Clazz clazz) {
        return clazz.isAssignableFrom(this.getClazz());
    }

}
