package jvm.rtda;

import jvm.rtda.heap.Method;

/**
 * 栈帧
 */
public class Frame {


    /**
     * 局部变量表
     */
    LocalVars localVars;

    /**
     * 单项链表,下个指针
     */
    Frame lower;

    /**
     * 操作数栈
     */
    OperandStack operandStack;

    Thread thread;

    int nextPC;

    Method method;

    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        localVars = new LocalVars(method.getMaxLocals());
        operandStack = new OperandStack(method.getMaxStack());
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public Frame getLower() {
        return lower;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public Thread getThread() {
        return thread;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    public int getNextPC() {
        return nextPC;
    }

    public Method getMethod() {
        return method;
    }

    public void revertNextPc() {
        nextPC = thread.pc;
    }

}
