package org.rockyang.tio.core.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.rockyang.tio.common.starter.configuration.TioClusterProperties;

/**
 * Tio 集群配置
 * @author yangjian
 * @author fanpan26
 * */
@ConfigurationProperties(prefix = "tio.core.cluster")
public class TioServerClusterProperties extends TioClusterProperties {

}
