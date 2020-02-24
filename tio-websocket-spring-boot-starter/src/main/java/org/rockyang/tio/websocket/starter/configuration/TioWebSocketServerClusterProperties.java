package org.rockyang.tio.websocket.starter.configuration;

import org.rockyang.tio.common.starter.configuration.TioClusterProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanpan26
 * */
@ConfigurationProperties("tio.websocket.cluster")
public class TioWebSocketServerClusterProperties extends TioClusterProperties { }
