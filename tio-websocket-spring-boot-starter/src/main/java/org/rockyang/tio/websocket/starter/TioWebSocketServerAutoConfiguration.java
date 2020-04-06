package org.rockyang.tio.websocket.starter;


import org.rockyang.tio.common.starter.RedisInitializer;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerClusterProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerRedisClusterProperties;
import org.rockyang.tio.websocket.starter.configuration.TioWebSocketServerSslProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.tio.cluster.redisson.RedissonTioClusterTopic;
import org.tio.core.intf.GroupListener;
import org.tio.core.stat.IpStatListener;
import org.tio.server.ServerGroupContext;
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
    @Qualifier(value = "wsIpStatListener")
    private IpStatListener tioWebSocketIpStatListener;

    @Autowired(required = false)
    @Qualifier(value = "wsGroupListener")
    private GroupListener tioWebSocketGroupListener;

    @Autowired(required = false)
    @Qualifier(value = "wsServerAioListener")
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
    @Qualifier(value = "wsRedissonTioClusterTopic")
    private RedissonTioClusterTopic wsRedissonTioClusterTopic;

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
    public ServerGroupContext wsServerGroupContext(TioWebSocketServerBootstrap bootstrap){
        return bootstrap.getServerGroupContext();
    }

    @Bean(destroyMethod="shutdown")
    @ConditionalOnProperty(value = "tio.websocket.cluster.enabled",havingValue = "true")
    @ConditionalOnMissingBean(name = "wsRedisInitializer")
    public RedisInitializer wsRedisInitializer(ApplicationContext applicationContext) {
        return new RedisInitializer(redisConfig, applicationContext);
    }


    /**
     *  RedissonTioClusterTopic  with  RedisInitializer
     * */
    @Bean
    @ConditionalOnBean(name = "wsRedisInitializer")
    @ConditionalOnMissingBean(name = "wsRedissonTioClusterTopic")
    public RedissonTioClusterTopic wsRedissonTioClusterTopic(@Qualifier(value = "wsRedisInitializer") RedisInitializer redisInitializer) {
        return new RedissonTioClusterTopic(CLUSTER_TOPIC_CHANNEL, redisInitializer.getRedissonClient());
    }
}
