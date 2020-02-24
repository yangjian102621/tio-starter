package org.rockyang.tio.websocket.starter.configuration;

import org.rockyang.tio.common.starter.configuration.TioRedisClusterProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fyp
 */
@ConfigurationProperties("tio.websocket.cluster.redis")
public class TioWebSocketServerRedisClusterProperties extends TioRedisClusterProperties { }
