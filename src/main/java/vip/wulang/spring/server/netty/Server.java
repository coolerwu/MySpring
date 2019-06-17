package vip.wulang.spring.server.netty;

import io.netty.channel.ChannelHandler;

import java.util.List;

/**
 * Server class.
 *
 * @author coolerwu
 * @version 1.0
 */
public interface Server {
    /**
     * Default start.
     *
     * @throws InterruptedException exception
     */
    void start() throws InterruptedException;

    /**
     * Init.
     */
    void init();

    /**
     * Add init channel handler.
     *
     * @param handlerList channel handler list
     */
    void add(List<ChannelHandler> handlerList);

    /**
     * Add init channel handler.
     *
     * @param handler channel handler
     */
    void add(ChannelHandler handler);
}
