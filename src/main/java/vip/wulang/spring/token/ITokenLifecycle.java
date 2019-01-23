package vip.wulang.spring.token;

/**
 * This class represents lifecycle of token. It contains init, start, pause, end.
 *
 * @author CoolerWu on 2019/1/14.
 * @version 1.0
 */
public interface ITokenLifecycle extends ITokenFactory {
    void init();

    void start();

    void pause();

    void end();
}
