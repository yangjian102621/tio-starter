package org.rockyang.tio.core.starter;


import org.rockyang.tio.common.starter.RedisInitializer;
import org.rockyang.tio.core.starter.configuration.TioServerClusterProperties;
import org.rockyang.tio.core.starter.configuration.TioServerProperties;
import org.rockyang.tio.core.starter.configuration.TioServerRedisClusterProperties;
import org.rockyang.tio.core.starter.configuration.TioServerSslProperties;
import org.rockyang.tio.core.starter.listener.SocketServerAioListener;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.tio.server.intf.ServerAioHandler;


/**
 * @author fanpan26
 * @author yangjian
 * */
@Configuration
@Import(TioServerInitializerConfiguration.class)
@ConditionalOnBean(TioServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties({
        TioServerProperties.class,
        TioServerClusterProperties.class,
        TioServerRedisClusterProperties.class,
        TioServerSslProperties.class})
public class TioServerAutoConfiguration {

    /**
     *  cluster topic channel
     * */
    private static final String CLUSTER_TOPIC_CHANNEL = "tio_server_spring_boot_starter";

    @Autowired(required = false)
    private ServerAioHandler serverAioHandler;

    @Autowired(required = false)
    private IpStatListener ipStatListener;

    @Autowired(required = false)
    private GroupListener groupListener;

    @Autowired(required = false)
    private SocketServerAioListener serverAioListener;

    @Autowired
    private TioServerClusterProperties clusterProperties;

    @Autowired
    private TioServerRedisClusterProperties redisConfig;

    @Autowired
    private TioServerProperties serverProperties;

    @Autowired
    private TioServerSslProperties serverSslProperties;

    @Autowired(required = false)
    private RedissonTioClusterTopic redissonTioClusterTopic;

    /**
     * Tio Server bootstrap
     * */
    @Bean
    public TioServerBootstrap tioServerBootstrap() {
        return TioServerBootstrap.getInstance(
                serverProperties,
                clusterProperties,
                serverSslProperties,
                redissonTioClusterTopic,
                ipStatListener,
                groupListener,
                serverAioHandler,
                serverAioListener);
    }

    @Bean
    @ConditionalOnProperty(value = "tio.core.cluster.enabled",havingValue = "true",matchIfMissing = true)
    @ConditionalOnMissingBean(RedisInitializer.class)
    public RedisInitializer redisInitializer(ApplicationContext applicationContext){
        return new RedisInitializer(redisConfig,applicationContext);
    }


    /**
     *  RedissonTioClusterTopic  with  RedisInitializer
     * */
    @Bean
    @ConditionalOnBean(RedisInitializer.class)
    @ConditionalOnMissingBean(RedissonTioClusterTopic.class)
    public RedissonTioClusterTopic redissonTioClusterTopic(RedisInitializer redisInitializer) {
        return new RedissonTioClusterTopic(CLUSTER_TOPIC_CHANNEL, redisInitializer.getRedissonClient());
    }
}
