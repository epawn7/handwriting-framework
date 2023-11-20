package heartbeat.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import ioc.anno.Autowire;
import ioc.anno.Compontment;

@Compontment
public class ServerChannelInitializer extends ChannelInitializer<Channel> {

    @Autowire
    private HeartBeatHandler heartBeatHandler = new HeartBeatHandler();


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(4, 0, 0));
        JsonToObjCodec<HeartBeatMsg> heartBeatMsgJsonToObjCodec = new JsonToObjCodec<>(HeartBeatMsg.class);
        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(heartBeatMsgJsonToObjCodec);
        pipeline.addLast(heartBeatHandler);
    }

}
