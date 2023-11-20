package heartbeat.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import ioc.anno.Compontment;
import util.Log;

@Compontment
public class NodeManager {

    private AtomicInteger idWorker;

    private ConcurrentHashMap<Integer, ServerNode> nodeMap;

    private AttributeKey<Integer> idKeyAttr = new AttributeKey<>("id");

    private static NodeManager instance = new NodeManager();

    private NodeManager() {
        idWorker = new AtomicInteger(0);
        nodeMap = new ConcurrentHashMap<>();
    }

    public static NodeManager getInstance() {
        return instance;
    }

    public void addNode(Integer id, ChannelHandlerContext ctx, Long sendTime) {
        ServerNode node = new ServerNode();
        node.setId(id);
        node.setLastBeatTime(sendTime);
        node.setContext(ctx);
        nodeMap.put(id, node);
        setNodeId(ctx, id);
        Log.info("new node {} added", id);
    }

    public Integer getNodeId() {
        return idWorker.incrementAndGet();
    }


    public Integer nodeHeartBeat(ChannelHandlerContext ctx, Long time) {
        Integer nodeId = getNodeId(ctx);
        ServerNode node = nodeMap.get(nodeId);
        node.setLastBeatTime(time);
        Log.info("node {} heart beat", nodeId);
        return nodeId;
    }

    public void nodeLoss(ChannelHandlerContext ctx) {
        Integer nodeId = getNodeId(ctx);
        ServerNode node = nodeMap.remove(nodeId);
        ctx.close();
        Log.info("node {} loss", nodeId);
    }

    private void setNodeId(ChannelHandlerContext ctx, Integer id) {
        Attribute<Integer> valAttr = ctx.attr(idKeyAttr);
        valAttr.set(id);
    }

    private Integer getNodeId(ChannelHandlerContext ctx) {
        Attribute<Integer> valAttr = ctx.attr(idKeyAttr);
        Integer id = valAttr.get();
        if (id == null) {
            throw new RuntimeException("获取节点失败");
        }
        return id;
    }

}
