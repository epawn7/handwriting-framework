package jvm.instructions.control;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.instructions.base.BranchUtil;
import jvm.rtda.Frame;

/**
 * 如果case值可以编码成一个索引表(case中的数值是连续的)，则实现成tableswitch指令
 */
public class TABLE_SWITCH implements Instruction {

    /**
     * 应默认情况下执行跳转所需的字节码偏移量
     */
    int defaultOffset;

    /**
     * 记录case的取值范围
     */
    int low;

    /**
     * 记录case的取值范围
     */
    int high;

    /**
     * 里面存放high-low+1个int值，
     * 对应各种case情况下，
     * 执行跳转所需的字节码偏移量
     */
    int[] jumpOffsets;


    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt();
        low = reader.readInt();
        high = reader.readInt();
        int jumpOffsetsCount = high - low + 1;
        jumpOffsets = reader.readInts(jumpOffsetsCount);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.getOperandStack().popInt();
        int offset;
        if (index < high && index > low) {
            offset = jumpOffsets[index - low];
        } else {
            offset = defaultOffset;
        }
        BranchUtil.branch(frame, offset);
    }

}
