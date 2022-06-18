package com.sun;

import com.sun.entity.Request;
import com.sun.entity.Response;
import com.sun.netty.NettyClient;

public class Transporters {


    public static Response send(Request request){

        NettyClient nettyClient = new NettyClient("127.0.0.1", 8080);

        nettyClient.connect(nettyClient.getInetSocketAddress());

        Response send = nettyClient.send(request);

        return send;

    }
}
