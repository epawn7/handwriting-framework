package jvm.rtda;

/**
 * java虚拟机栈
 */
public class Stack {

    int maxSize;

    int size;

    Frame topFrame;

    public Stack(int maxSize) {
        this.maxSize = maxSize;
        size = 0;
    }

    public void push(Frame item) {
        if (size == maxSize) {
            throw new RuntimeException("java虚拟机栈溢出");
        }
        item.lower = topFrame;
        size++;
        topFrame = item;
    }

    public Frame pop() {
        if (size == 0) {
            throw new RuntimeException("java虚拟机栈为空");
        }
        Frame ret = topFrame;
        topFrame = topFrame.lower;
        size--;
        return ret;
    }

    public Frame peek() {
        return topFrame;
    }

    public boolean isEmpty() {
        return topFrame == null;
    }

}
