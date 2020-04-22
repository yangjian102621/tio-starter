package org.rockyang.tio.websocket.starter;


import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerClusterProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerRedisClusterProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerSslProperties;
import org.rockyang.tio.websocket.starter.listener.WsGroupListener;
import org.rockyang.tio.websocket.starter.listener.WsIpStatListener;
import org.rockyang.tio.websocket.starter.redisson.WsRedisInitializer;
import org.rockyang.tio.websocket.starter.redisson.WsRedissonTioClusterTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.tio.websocket.server.WsServerAioListener;
import org.tio.websocket.server.handler.IWsMsgHandler;


/**
 * @author fanpan26
 * */
@Configuration
@Import(TioWebSocketServerInitializerConfiguration.class)
@ConditionalOnBean(TioWebSocketServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties({ TioWebSocketServerProperties.class,
        TioWebSocketServerClusterProperties.class,
        TioWebSocketServerRedisClusterProperties.class,
        TioWebSocketServerSslProperties.class})
public class TioWebSocketServerAutoConfiguration {

    /**
     *  cluster topic channel
     * */
    private static final String CLUSTER_TOPIC_CHANNEL = "tio_ws_spring_boot_starter";

    @Autowired(required = false)
    private IWsMsgHandler tioWebSocketMsgHandler;

    @Autowired(required = false)
    private WsIpStatListener tioWebSocketIpStatListener;

    @Autowired(required = false)
    private WsGroupListener tioWebSocketGroupListener;

    @Autowired(required = false)
    private WsServerAioListener tioWebSocketServerAioListener;

    @Autowired
    private TioWebSocketServerClusterProperties clusterProperties;

    @Autowired
    private TioWebSocketServerRedisClusterProperties redisConfig;

    @Autowired
    private TioWebSocketServerProperties serverProperties;

    @Autowired
    private TioWebSocketServerSslProperties serverSslProperties;

    @Autowired(required = false)
    private WsRedissonTioClusterTopic wsRedissonTioClusterTopic;

    /**
     * Tio WebSocket Server bootstrap
     * */
    @Bean
    public TioWebSocketServerBootstrap webSocketServerBootstrap() {
        return TioWebSocketServerBootstrap.getInstance(serverProperties,
                clusterProperties,
                serverSslProperties,
                wsRedissonTioClusterTopic,
                tioWebSocketMsgHandler,
                tioWebSocketIpStatListener,
                tioWebSocketGroupListener,
                tioWebSocketServerAioListener);
    }

    @Bean
    @ConditionalOnProperty(value = "tio.websocket.cluster.enabled", havingValue = "true")
    @ConditionalOnMissingBean(WsRedisInitializer.class)
    public WsRedisInitializer wsRedisInitializer(ApplicationContext applicationContext) {
        return new WsRedisInitializer(redisConfig, applicationContext);
    }


    /**
     *  RedissonTioClusterTopic  with  RedisInitializer
     * */
    @Bean
    @ConditionalOnBean(WsRedisInitializer.class)
    @ConditionalOnMissingBean(WsRedissonTioClusterTopic.class)
    public WsRedissonTioClusterTopic wsRedissonTioClusterTopic(WsRedisInitializer wsRedisInitializer) {
        return new WsRedissonTioClusterTopic(CLUSTER_TOPIC_CHANNEL, wsRedisInitializer.getRedissonClient());
    }
}
