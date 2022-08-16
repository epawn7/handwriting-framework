package jvm.instructions.control;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;

/**
 * 2022-08-03
 */
public class LOOKUP_SWITCH implements Instruction {

    /**
     * 应默认情况下执行跳转所需的字节码偏移量
     */
    int defaultOffset;

    /**
     * matchOffsets大小
     */
    int npairs;

    /**
     * 采用key-val的模式,前面为key,后一个为val
     */
    int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt();
        npairs = reader.readInt();
        matchOffsets = reader.readInts(npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
        int key = frame.getOperandStack().popInt();
        for (int i = 0; i < matchOffsets.length; i += 2) {
            if (matchOffsets[i] == key) {
                BranchUtil.branch(frame, matchOffsets[i + 1]);
                return;
            }
        }
        BranchUtil.branch(frame, defaultOffset);
    }

}
