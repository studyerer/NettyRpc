package com.sun.netty;

import com.sun.client.Client;
import com.sun.coder.Decoder;
import com.sun.coder.Encoder;
import com.sun.entity.Request;
import com.sun.entity.Response;
import com.sun.netty.handler.ClientHandler;
import com.sun.serialization.JsonSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

public class NettyClient implements Client {

    private EventLoopGroup eventLoopGroup;
    private Channel channel;
    private ClientHandler clientHandler;

    private String host;
    private int port;

    public NettyClient(String host, int port){
        this.port = port;
        this.host = host;

    }

    @Override
    public Response send(final Request request) {
        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientHandler.getResponse(request.getRequestId());
    }

    @Override
    public void connect(final InetSocketAddress inetSocketAddress) {

        clientHandler = new ClientHandler();

        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
                        pipeline.addLast(new Encoder(Request.class,new JsonSerialization()));
                        pipeline.addLast(new Decoder(Response.class,new JsonSerialization()));
                        pipeline.addLast(clientHandler);
                    }
                });

        try {
            channel = bootstrap.connect(inetSocketAddress).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(host,port);
    }

    @Override
    public void close() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }
}
