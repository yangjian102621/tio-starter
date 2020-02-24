package org.rockyang.tio.websocket.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author fanpan26
 * */
@Configuration
public class TioWebSocketServerInitializerConfiguration
        implements SmartLifecycle, Ordered {

    private int order = 1;
    private boolean running = false;

    @Autowired
    private TioWebSocketServerBootstrap webSocketServerBootstrap;


    @Override
    public void start() {
        new Thread(() -> {
            if (!isRunning()) {
                webSocketServerBootstrap.contextInitialized();
                running = true;
            }
        }, "tioWsServer").start();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public int getOrder() {
        return order;
    }
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback)
    {
        /**
         * 修复 spring-boot devtool 热启动模式下会抛出异常的 Bug
         * support.DefaultLifecycleProcessor : Failed to shut down 1 bean with phase value 0 within timeout of 30000
         * 而且在 idea 下面需要点击两次关闭应用才会退出
         */
        callback.run();
        running = false;
    }

}
