package heartbeat.server;

import java.net.InetSocketAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HeartBeatServer {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(7770))
                .childHandler(new ServerChannelInitializer());
        try {
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
