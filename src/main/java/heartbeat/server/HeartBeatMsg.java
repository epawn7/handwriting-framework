package heartbeat.server;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeartBeatMsg {

    private Integer id;

    private Long sendTime;

    private HeartBeatMsgEnum msg;

    public static HeartBeatMsg beatMsg(Integer nodeId) {
        return HeartBeatMsg.builder().id(nodeId).msg(HeartBeatMsgEnum.BEAT).sendTime(System.currentTimeMillis())
                .build();
    }

    public static HeartBeatMsg ackMsg(Integer nodeId) {
        return HeartBeatMsg.builder().id(nodeId).msg(HeartBeatMsgEnum.ACK).sendTime(System.currentTimeMillis()).build();
    }

}
