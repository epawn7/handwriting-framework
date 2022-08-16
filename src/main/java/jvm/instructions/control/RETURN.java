package jvm.instructions.control;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.rtda.Frame;

/**
 * 指令用于没有返回值的情况
 */
public class RETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getThread().popFrame();
    }

}
