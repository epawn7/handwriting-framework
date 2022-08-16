package jvm.rtda.heap.ref;

import jvm.clazz.constant.ConstantFieldref;
import jvm.rtda.heap.Clazz;
import jvm.rtda.heap.ConstantPool;
import jvm.rtda.heap.Field;

/**
 * 字段符号引用
 */
public class FieldRef extends MemberRef {

    Field field;

    public FieldRef(ConstantPool constantPool, ConstantFieldref constantFieldref) {
        super(constantPool);
        super.className = constantFieldref.getClassName();
        super.name = constantFieldref.getName();
        super.descriptor = constantFieldref.getDescriptor();
    }

    public Field resolvedField() {
        if (field == null) {
            this.resolvedFieldRef();
        }
        return field;
    }

    private void resolvedFieldRef() {
        Clazz rootClazz = this.constantPool.getClazz();
        Clazz fieldClazz = this.resolvedClass();
        Field field1 = lookupField(fieldClazz, this.name, this.descriptor);
        if (field1 == null) {
            throw new RuntimeException(this.name + "字段不存在");
        }
        if (!field1.isAccessibleTo(rootClazz)) {
            throw new RuntimeException("非法访问");
        }
        this.field = field1;
    }

    /**
     * 在类中查询字段.
     * 先查找当前类,再查找接口中字段,最后查找父类中的字段.
     *
     * @param clazz 字段所在的类
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @return 字段信息
     */
    private Field lookupField(Clazz clazz, String name, String descriptor) {
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if (f.getName().equals(name) && f.getDescriptor().equals(descriptor)) {
                return f;
            }
        }
        Clazz[] interfaces = clazz.getInterfaces();
        if (interfaces != null) {
            for (Clazz inface : interfaces) {
                Field field1 = lookupField(inface, name, descriptor);
                if (field1 != null) {
                    return field1;
                }
            }
        }
        Clazz supperClass = clazz.getSupperClass();
        if (supperClass != null) {
            return lookupField(supperClass, name, descriptor);
        }
        return null;
    }

}
