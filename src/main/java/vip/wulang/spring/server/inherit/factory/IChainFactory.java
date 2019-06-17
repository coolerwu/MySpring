package vip.wulang.spring.server.inherit.factory;

/**
 * Chain factory interface.
 *
 * @author coolerwu
 * @version 1.0
 */
public interface IChainFactory<T> {
    void addLast(T chain);

    void addFirst(T chain);

    void remove(T chain);
}
