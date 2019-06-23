package org.tio.core.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author fanpan26
 * */
@Configuration
public class TioServerInitializerConfiguration
        implements SmartLifecycle, Ordered {

    private int order = 1;
    private boolean running = false;

    @Autowired
    private TioServerBootstrap tioServerBootstrap;


    @Override
    public void start() {
        new Thread(() -> {
            tioServerBootstrap.contextInitialized();
            running = true;
        }).start();
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
    public void stop(Runnable runnable) {

    }

}
