package vip.wulang.spring.server.netty.handler;

import io.netty.channel.*;

/**
 * @author coolerwu
 * @version 1.0
 */
@ChannelHandler.Sharable
public class OutboundInitHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("============");
//        super.write(ctx, msg, promise);
        ctx.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
        promise.setSuccess();
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==========");
        super.read(ctx);
    }
}
