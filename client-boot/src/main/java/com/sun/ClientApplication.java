package com.sun;

import com.sun.api.IQueryStudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class ClientApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class);
        IQueryStudentService helloService = context.getBean(IQueryStudentService.class);
        System.out.println(helloService.getName("sun"));
    }
}
