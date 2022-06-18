package com.sun.netty.handler;

import com.sun.coder.Decoder;
import com.sun.coder.Encoder;
import com.sun.entity.Request;
import com.sun.entity.Response;
import com.sun.serialization.JsonSerialization;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new Encoder(Response.class,new JsonSerialization()));
        pipeline.addLast(new Decoder(Request.class,new JsonSerialization()));
        pipeline.addLast(serverHandler);

    }
}
