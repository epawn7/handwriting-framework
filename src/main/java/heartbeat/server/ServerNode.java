package heartbeat.server;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class ServerNode {

    private Integer id;

    private Long lastBeatTime;

    private String ip;

    private ChannelHandlerContext context;

}
