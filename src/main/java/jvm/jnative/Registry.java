package jvm.jnative;

import java.util.HashMap;

/**
 * 注册表
 */
public class Registry {

    private HashMap<String, NativeMethod> nativeMethodHashMap;

    private NativeMethod emptyMethod;

    private static Registry instance;

    public static Registry getInstance() {
        if (instance == null) {
            synchronized (Registry.class) {
                if (instance == null) {
                    instance = new Registry();
                }
            }
        }
        return instance;
    }

    private Registry() {
        nativeMethodHashMap = new HashMap<>();
        emptyMethod = frame -> {
            System.out.println("调用空方法");
        };
    }

    public void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        nativeMethodHashMap.put(className + "~" + methodName + "~" + methodDescriptor, method);
    }

    public NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        NativeMethod nativeMethod = nativeMethodHashMap.get(className + "~" + methodName + "~" + methodDescriptor);
        if (methodName.equals("registerNatives") && methodDescriptor.equals("()V")) {
            return emptyMethod;
        }
        return nativeMethod;
    }

}
