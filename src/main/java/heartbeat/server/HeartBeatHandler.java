package heartbeat.server;

import java.io.IOException;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import ioc.anno.Autowire;
import ioc.anno.Compontment;
import util.Log;

@Sharable
@Compontment
public class HeartBeatHandler extends SimpleChannelInboundHandler<HeartBeatMsg> {

    @Autowire
    private NodeManager nodeManager = NodeManager.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatMsg msg) throws Exception {
        Log.debug("接收到消息");
        switch (msg.getMsg()) {
            case HELLO:
                HeartBeatMsg helloReply = HeartBeatMsg.builder().id(nodeManager.getNodeId()).msg(HeartBeatMsgEnum.ACK)
                        .sendTime(System.currentTimeMillis()).build();
                ctx.writeAndFlush(helloReply);
                break;
            case ACK:
                nodeManager.addNode(msg.getId(), ctx, msg.getSendTime());
                break;
            case BEAT:
                Integer node = nodeManager.nodeHeartBeat(ctx, msg.getSendTime());
                HeartBeatMsg beatReply = HeartBeatMsg.builder().id(node).msg(HeartBeatMsgEnum.BEAT)
                        .sendTime(System.currentTimeMillis()).build();
                ctx.writeAndFlush(beatReply);
                break;
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                nodeManager.nodeLoss(ctx);
                System.out.println("长时间为未调用");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            nodeManager.nodeLoss(ctx);
        }
    }

}
