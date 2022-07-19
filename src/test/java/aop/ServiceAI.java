package aop;

/**
 * @author jinfan 2022-06-28
 */
public interface ServiceAI {

    @LogAnn1
    void print();

    @LogAnn1
    void print2();

}
