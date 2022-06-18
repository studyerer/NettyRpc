package com.sun;

import com.sun.api.IQueryStudentService;
import com.sun.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        NettyServer service = context.getBean(NettyServer.class);
        service.start();

    }
}
