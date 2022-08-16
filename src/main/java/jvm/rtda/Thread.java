package jvm.rtda;

/**
 * 运行线程
 */
public class Thread {

    int pc;

    Stack stack;

    public Thread() {
        this.stack = new Stack(1024);
    }

    public void pushFrame(Frame frame) {
        stack.push(frame);
    }

    public Frame popFrame() {
        return stack.pop();
    }

    public Frame topFrame() {
        return stack.peek();
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

}
