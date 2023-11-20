package heartbeat.server;

import java.util.List;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class JsonToObjCodec<T> extends MessageToMessageCodec<String, T> {

    private final Class<T> clazz;

    public JsonToObjCodec(Class<T> clazz) {
        super(String.class, clazz);
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, List<Object> out) throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        out.add(json);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        Gson gson = new Gson();
        T obj = gson.fromJson(msg, clazz);
        out.add(obj);
    }

}
