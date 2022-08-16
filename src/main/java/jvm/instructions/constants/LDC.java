package jvm.instructions.constants;

import jvm.instructions.base.Index8Instruction;
import jvm.rtda.Frame;

/**
 * 令从运行时常量池中加载常量值，并把它推入操作数栈
 */
public class LDC extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LDC_Logic._ldc(frame, index);
    }

}
