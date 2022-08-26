package jvm.instructions;

import jvm.rtda.Frame;
import jvm.rtda.Thread;
import jvm.rtda.heap.Method;
import util.Log;

public class Interpreter {


    public void interpret(Method method, boolean logInst) {
        Thread thread = new Thread();

        Frame frame = new Frame(thread, method);
        thread.pushFrame(frame);
        loop(thread, logInst);
    }

    private void loop(Thread thread, boolean logInst) {
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            Frame frame = thread.topFrame();
            int pc = frame.getNextPC();
            thread.setPc(pc);
            reader.reset(frame.getMethod().getCode(), pc);

            try {
                int opCode = reader.readUint8();
                Instruction instruction = InstructionFactory.createInstruction(opCode);
                instruction.fetchOperands(reader);
                frame.setNextPC(reader.pc);
                if (logInst) {
                    logInstruction(frame, instruction);
                }
                instruction.execute(frame);
                if (thread.isStackEmpty()) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        }
    }

    private void logInstruction(Frame frame, Instruction instruction) {
        Method method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        int pc = frame.getThread().getPc();
        Log.debug("className:{},methodName:{},pc:{},inst:{}", className, methodName, pc,
                instruction.getClass().getSimpleName());
    }

    private void logFrames(Thread thread) {

    }

}
