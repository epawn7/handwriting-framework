package jvm.jnative;

import jvm.rtda.Frame;

@FunctionalInterface
public interface NativeMethod {

    void invoke(Frame frame);

}
