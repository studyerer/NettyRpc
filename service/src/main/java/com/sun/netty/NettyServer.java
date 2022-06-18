package com.sun.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;



@Data
@Component
public class NettyServer {

    private final ServerBootstrap serverBootstrap;

    private final InetSocketAddress tcpPort;

    public NettyServer(ServerBootstrap serverBootstrap, InetSocketAddress inetSocketAddress){

        this.serverBootstrap = serverBootstrap;
        this.tcpPort = inetSocketAddress;
    }

    private Channel serverChannel;

    public void start() throws InterruptedException {

        serverChannel =  serverBootstrap.bind(tcpPort).sync().channel().closeFuture().channel();
    }

    @PreDestroy
    public void stop() {

        if(serverChannel != null){
            serverChannel.close();
            serverChannel.parent().close();
        }
    }
}
