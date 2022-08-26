package jvm.instructions.reserved;

import jvm.instructions.base.NoOperandsInstruction;
import jvm.jnative.NativeMethod;
import jvm.jnative.Registry;
import jvm.rtda.Frame;
import jvm.rtda.heap.Method;

/**
 * 调用虚拟方法
 * 自定义的方法
 */
public class INVOKE_NATIVE extends NoOperandsInstruction {

    Registry registry = Registry.getInstance();

    @Override
    public void execute(Frame frame) {
        Method method = frame.getMethod();

        NativeMethod nativeMethod = registry.findNativeMethod(method.getClazz().getName(), method.getName(),
                method.getDescriptor());
        if (nativeMethod == null) {
            RuntimeException runtimeException = new RuntimeException(
                    "调用本地方法不存在:" + method.getClazz().getName() + "," + method.getName() + "," + method.getDescriptor());
            throw runtimeException;
        }
        nativeMethod.invoke(frame);
    }

}
