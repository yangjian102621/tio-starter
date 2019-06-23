package org.rockyang.tio.core.server;


import org.tio.core.starter.annotation.EnableTioServerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangjian
 */
@SpringBootApplication
@EnableTioServerServer
public class TioServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TioServerApplication.class, args);
    }
}
