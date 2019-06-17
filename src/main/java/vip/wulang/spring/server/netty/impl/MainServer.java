package vip.wulang.spring.server.netty.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import vip.wulang.spring.server.netty.Server;

import java.util.LinkedList;
import java.util.List;

/**
 * @author coolerwu
 * @version 1.0
 */
public class MainServer implements Server {
    private final int port;
    private ServerBootstrap bootstrap;
    private boolean init;
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private List<ChannelHandler> handlerList;

    public MainServer(int port) {
        if (port == 0) {
            port = 80;
        }

        this.port = port;
        this.init = false;
        this.handlerList = new LinkedList<>();
    }

    @Override
    public void start() throws InterruptedException {
        if (!init) {
            init();
        }

        try {
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
            future.addListener(f -> {
                if (f.isSuccess()) {
                    System.out.println("success");
                }
            });
        } finally {
            parentGroup.shutdownGracefully().sync();
            childGroup.shutdownGracefully().sync();
        }
    }

    @Override
    public void init() {
        parentGroup = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                .localAddress(port)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        handlerList.forEach(pipeline::addLast);
                    }
                });
        init = true;
    }

    @Override
    public void add(List<ChannelHandler> handlerList) {
        if (handlerList == null || handlerList.size() == 0) {
            throw new NullPointerException();
        }

        this.handlerList.addAll(handlerList);
    }

    @Override
    public void add(ChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException();
        }

        this.handlerList.add(handler);
    }
}
