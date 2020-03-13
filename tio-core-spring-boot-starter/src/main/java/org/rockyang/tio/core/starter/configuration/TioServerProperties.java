package org.rockyang.tio.core.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.rockyang.tio.common.starter.configuration.TioProperties;

/**
 * @author yangjian
 * */
@ConfigurationProperties(prefix = "tio.core.server")
public class TioServerProperties extends TioProperties {

    /**
     * 是否使用了 spring-boot-devtools 热加载模式
     */
    private boolean useSpringBootDevtools = false;

    public TioServerProperties() {
        setPort(6789);
        setHeartbeatTimeout(5000);
        setGroupContextName("tio-server-spring-boot-starter");
    }

    public boolean isUseSpringBootDevtools()
    {
        return useSpringBootDevtools;
    }

    public void setUseSpringBootDevtools(boolean useSpringBootDevtools)
    {
        this.useSpringBootDevtools = useSpringBootDevtools;
    }
}
