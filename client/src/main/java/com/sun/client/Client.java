package com.sun.client;

import com.sun.entity.Request;
import com.sun.entity.Response;

import java.net.InetSocketAddress;

public interface Client {

    Response send(Request request);

    void connect(InetSocketAddress inetSocketAddress);

    InetSocketAddress getInetSocketAddress();

    void close();
}
