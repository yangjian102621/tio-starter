package org.rockyang.tio.websocket.starter.configuration;

import org.rockyang.tio.common.starter.configuration.TioSslProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fyp
 */
@ConfigurationProperties("tio.websocket.ssl")
public class TioWebSocketServerSslProperties extends TioSslProperties {}
