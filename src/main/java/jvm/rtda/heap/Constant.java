package jvm.rtda.heap;

/**
 * 运行时常量池常量
 */
public class Constant {

    Object val;

    /**
     * 类型
     */
    int type;

    public Constant(Object val, int type) {
        this.val = val;
        this.type = type;
    }

    public Object getVal() {
        return val;
    }

    public int getType() {
        return type;
    }

}
