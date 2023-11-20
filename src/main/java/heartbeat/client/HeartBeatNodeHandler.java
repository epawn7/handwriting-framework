package heartbeat.client;

import heartbeat.server.HeartBeatMsg;
import heartbeat.server.HeartBeatMsgEnum;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import util.Log;

public class HeartBeatNodeHandler extends SimpleChannelInboundHandler<HeartBeatMsg> {

    private Integer id;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.debug("建立连接");
        HeartBeatMsg hello = HeartBeatMsg.builder().msg(HeartBeatMsgEnum.HELLO).sendTime(System.currentTimeMillis())
                .build();
        ChannelFuture channelFuture = ctx.writeAndFlush(hello);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatMsg msg) throws Exception {
        switch (msg.getMsg()) {
            case ACK:
                msg.setMsg(HeartBeatMsgEnum.ACK);
                ctx.writeAndFlush(msg);
                break;
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(HeartBeatMsg.beatMsg(id));
                System.out.println("发送心跳信息");
            }
        }
    }

}
