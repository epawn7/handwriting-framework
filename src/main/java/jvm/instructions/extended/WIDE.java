package jvm.instructions.extended;

import jvm.instructions.BytecodeReader;
import jvm.instructions.Instruction;
import jvm.instructions.loads.ALOAD;
import jvm.instructions.loads.DLOAD;
import jvm.instructions.loads.FLOAD;
import jvm.instructions.loads.ILOAD;
import jvm.instructions.loads.LLOAD;
import jvm.instructions.math.IINC;
import jvm.instructions.stores.ASTORE;
import jvm.instructions.stores.DSTORE;
import jvm.instructions.stores.FSTORE;
import jvm.instructions.stores.ISTORE;
import jvm.instructions.stores.LSTORE;
import jvm.rtda.Frame;

/**
 * wide指令改变其他指令的行为，modifiedInstruction字段存放被改变的指令。
 */
public class WIDE implements Instruction {

    Instruction modifiedInstruction;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opcode = reader.readUint8();
        switch (opcode) {
            case 0x15:
                ILOAD iload = new ILOAD();
                iload.index = reader.readUint16();
                modifiedInstruction = iload;
                break;
            case 0x16:
                LLOAD lload = new LLOAD();
                lload.index = reader.readUint16();
                modifiedInstruction = lload;
                break;
            case 0x17:
                FLOAD fload = new FLOAD();
                fload.index = reader.readUint16();
                modifiedInstruction = fload;
                break;
            case 0x18:
                DLOAD dload = new DLOAD();
                dload.index = reader.readUint16();
                modifiedInstruction = dload;
                break;
            case 0x19:
                ALOAD aload = new ALOAD();
                aload.index = reader.readUint16();
                modifiedInstruction = aload;
                break;
            case 0x36:
                ISTORE istore = new ISTORE();
                istore.index = reader.readUint16();
                modifiedInstruction = istore;
                break;
            case 0x37:
                LSTORE lstore = new LSTORE();
                lstore.index = reader.readUint16();
                modifiedInstruction = lstore;
                break;
            case 0x38:
                FSTORE fstore = new FSTORE();
                fstore.index = reader.readUint16();
                modifiedInstruction = fstore;
                break;
            case 0x39:
                DSTORE dstore = new DSTORE();
                dstore.index = reader.readUint16();
                modifiedInstruction = dstore;
                break;
            case 0x3a:
                ASTORE astore = new ASTORE();
                astore.index = reader.readUint16();
                modifiedInstruction = astore;
                break;
            case 0x84:
                IINC iinc = new IINC();
                iinc.setIndex(reader.readUint16());
                iinc.setVal(reader.readUint16());
                modifiedInstruction = iinc;
                break;
            case 0xa9: // ret
                throw new RuntimeException("Unsupported opcode: 0xa9!");
        }
    }

    @Override
    public void execute(Frame frame) {
        modifiedInstruction.execute(frame);
    }

}
