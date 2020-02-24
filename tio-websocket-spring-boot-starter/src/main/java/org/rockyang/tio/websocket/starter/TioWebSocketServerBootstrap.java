package org.rockyang.tio.websocket.starter;


import org.rockyang.tio.common.starter.TioServerMsgHandlerNotFoundException;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerClusterProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerSslProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.cluster.TioClusterConfig;
import org.tio.cluster.redisson.RedissonTioClusterTopic;
import org.tio.core.intf.GroupListener;
import org.tio.core.stat.IpStatListener;
import org.tio.server.ServerGroupContext;
import org.tio.utils.Threads;
import org.tio.websocket.server.WsServerAioListener;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;

/**
 * @author fanpan26
 * @author yangjian
 * */
public final class TioWebSocketServerBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(TioWebSocketServerBootstrap.class);

    private static final String GROUP_CONTEXT_NAME = "tio-websocket-spring-boot-starter";
    
    private TioWebSocketServerProperties serverProperties;
    private TioWebSocketServerClusterProperties clusterProperties;
    private TioWebSocketServerSslProperties serverSslProperties;
    private RedissonTioClusterTopic redissonTioClusterTopic;
    private WsServerConfig wsServerConfig;
    private TioClusterConfig clusterConfig;
    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;
    private IWsMsgHandler tioWebSocketMsgHandler;
    private IpStatListener ipStatListener;
    private GroupListener groupListener;
    private WsServerAioListener serverAioListener;

    private static volatile TioWebSocketServerBootstrap tioWebSocketServerBootstrap;

    // 标记是否已经初始化
    private boolean initialized = false;

    // 这里采用单例模式，解决springboot devtools模式下，重复启动tio服务造成端口冲突问题
    public static TioWebSocketServerBootstrap getInstance(TioWebSocketServerProperties serverProperties,
                                                          TioWebSocketServerClusterProperties clusterProperties,
                                                          TioWebSocketServerSslProperties serverSslProperties,
                                                          RedissonTioClusterTopic redissonTioClusterTopic,
                                                          IWsMsgHandler tioWebSocketMsgHandler,
                                                          IpStatListener ipStatListener,
                                                          GroupListener groupListener,
                                                          WsServerAioListener serverAioListener)
    {
        if (tioWebSocketServerBootstrap == null) {
            synchronized (TioWebSocketServerBootstrap.class) {
                if (tioWebSocketServerBootstrap == null) {
                    tioWebSocketServerBootstrap = new TioWebSocketServerBootstrap(serverProperties,
                            clusterProperties,
                            serverSslProperties,
                            redissonTioClusterTopic,
                            tioWebSocketMsgHandler,
                            ipStatListener,
                            groupListener,
                            serverAioListener);
                }
            }
        }
        return tioWebSocketServerBootstrap;
    }

    private TioWebSocketServerBootstrap(TioWebSocketServerProperties serverProperties,
                                       TioWebSocketServerClusterProperties clusterProperties,
                                       TioWebSocketServerSslProperties serverSslProperties,
                                       RedissonTioClusterTopic redissonTioClusterTopic,
                                       IWsMsgHandler tioWebSocketMsgHandler,
                                       IpStatListener ipStatListener,
                                       GroupListener groupListener,
                                       WsServerAioListener serverAioListener) {
        this.serverProperties = serverProperties;
        this.clusterProperties = clusterProperties;
        this.serverSslProperties = serverSslProperties;

        logger.debug(serverSslProperties.toString());
        if (redissonTioClusterTopic == null) {
            logger.info("cluster mod closed");
        }
        this.redissonTioClusterTopic = redissonTioClusterTopic;
        this.ipStatListener = ipStatListener;
        this.groupListener = groupListener;
        this.serverAioListener = serverAioListener;
        this.tioWebSocketMsgHandler = tioWebSocketMsgHandler;
        afterSetProperties();
    }

    private void afterSetProperties(){
        if (this.tioWebSocketMsgHandler == null) {
            throw new TioServerMsgHandlerNotFoundException("No instance of IWsMsgHandler found");
        }
        if (this.ipStatListener == null){
            logger.warn("No instance of IpStatListener found");
        }
        if (this.groupListener == null){
            logger.warn("No instance of GroupListener found");
        }
    }


    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    public void contextInitialized() {
        if (initialized){
            logger.info("Tio WebSocket Server has already been initialized.");
            return;
        }
        logger.info("Initializing Tio WebSocket Server");

        try {
            initTioWebSocketConfig();
            initTioWebSocketServer();
            initTioWebSocketServerGroupContext();
            start();
            initialized = true;
        } catch (Throwable e) {
            logger.error("Can not start Tio WebSocket Server :", e);
            throw new RuntimeException("Can not start Tio WebSocket Server ", e);
        }
    }

    private void initTioWebSocketConfig() {
        this.wsServerConfig = new WsServerConfig(serverProperties.getPort());
        if (redissonTioClusterTopic != null && clusterProperties.isEnabled()) {
            this.clusterConfig = new TioClusterConfig(redissonTioClusterTopic);
            this.clusterConfig.setCluster4all(clusterProperties.isAll());
            this.clusterConfig.setCluster4bsId(true);
            this.clusterConfig.setCluster4channelId(clusterProperties.isChannel());
            this.clusterConfig.setCluster4group(clusterProperties.isGroup());
            this.clusterConfig.setCluster4ip(clusterProperties.isIp());
            this.clusterConfig.setCluster4user(clusterProperties.isUser());
        }
    }

    private void initTioWebSocketServer() throws Exception {
        TioWebSocketServerDefaultUuid tioUid = new TioWebSocketServerDefaultUuid(
                serverProperties.getWorkerId(),serverProperties.getDataCenter()
        );
        wsServerStarter = new WsServerStarter(wsServerConfig,
                tioWebSocketMsgHandler,
                tioUid,
                Threads.getTioExecutor(),
                Threads.getGroupExecutor());
    }

    private void initTioWebSocketServerGroupContext() {
        serverGroupContext = wsServerStarter.getServerGroupContext();
        serverGroupContext.setName(GROUP_CONTEXT_NAME);
        if (ipStatListener != null) {
            serverGroupContext.setIpStatListener(ipStatListener);
        }
        if(serverAioListener != null) {
            serverGroupContext.setServerAioListener(this.serverAioListener);
        }
        if (groupListener != null) {
            serverGroupContext.setGroupListener(groupListener);
        }
        if (serverProperties.getHeartbeatTimeout() > 0) {
            serverGroupContext.setHeartbeatTimeout(serverProperties.getHeartbeatTimeout());
        }
        //cluster config
        if (clusterConfig != null) {
            serverGroupContext.setTioClusterConfig(clusterConfig);
        }
        //ssl config
        if (serverSslProperties.isEnabled()){
            try {
                serverGroupContext.useSsl(serverSslProperties.getKeyStore(), serverSslProperties.getTrustStore(), serverSslProperties.getPassword());
            }catch (Exception e){
                //catch and log
                logger.error("Error occurred while initializing SSL Config",e);
            }
        }
    }

    private void start() throws IOException {
        wsServerStarter.start();
    }
}
