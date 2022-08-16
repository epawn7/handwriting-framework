package jvm.rtda.heap;

/**
 * 类成员抽象类.
 */
public abstract class ClassMember {

    short accessFlag;

    String name;

    String descriptor;

    Clazz clazz;

    public boolean isStastic() {
        return (accessFlag & AccessFlagConst.ACC_STATIC) != 0;
    }

    public boolean isFinal() {
        return (accessFlag & AccessFlagConst.ACC_FINAL) != 0;
    }

    public boolean isPublic() {
        return (accessFlag & AccessFlagConst.ACC_PUBLIC) != 0;
    }

    public boolean isProtected() {
        return (accessFlag & AccessFlagConst.ACC_PROTECTED) != 0;
    }

    public boolean isAbstract() {
        return (accessFlag & AccessFlagConst.ACC_ABSTRACT) != 0;
    }


    public boolean isPrivate() {
        return (accessFlag & AccessFlagConst.ACC_PRIVATE) != 0;
    }

    public boolean isAccessibleTo(Clazz c) {
        if (this.isPublic()) {
            return true;
        }
        if (isProtected()) {
            return clazz == c || c.getPackageName().equals(clazz.getPackageName()) || clazz.isSubClassOf(c);
        }
        if (!isPrivate()) {
            return c.getPackageName().equals(clazz.getPackageName());
        }
        return clazz == c;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public Clazz getClazz() {
        return clazz;
    }

}
