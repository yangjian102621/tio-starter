package org.rockyang.tio.core.server;


import org.rockyang.tio.websocket.starter.annotation.EnableTioWebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Tio Websocket Server application
 * @author yangjian
 */
@SpringBootApplication
@EnableTioWebSocketServer
public class TioWsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TioWsServerApplication.class, args);
    }
}
