package org.rockyang.tio.core.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.rockyang.tio.common.starter.configuration.TioSslProperties;

/**
 * SSL 连接配置
 * @author yangjian
 */
@ConfigurationProperties(prefix = "tio.core.ssl")
public class TioServerSslProperties extends TioSslProperties{
}
