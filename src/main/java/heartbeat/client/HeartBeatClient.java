package heartbeat.client;

import heartbeat.server.HeartBeatMsg;
import heartbeat.server.JsonToObjCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class HeartBeatClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class).remoteAddress("localhost", 7770)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 3, 0));
                        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new JsonToObjCodec<>(HeartBeatMsg.class));
                        pipeline.addLast(new HeartBeatNodeHandler());
                    }
                });
        ChannelFuture sync = bootstrap.connect().sync();
        sync.channel().closeFuture().sync();
        group.shutdownGracefully().sync();
    }

}
