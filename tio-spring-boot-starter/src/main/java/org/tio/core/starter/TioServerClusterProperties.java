package org.tio.core.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.tio.utils.hutool.StrUtil;

/**
 * Tio 集群配置
 * @author yangjian
 * @author fanpan26
 * */
@ConfigurationProperties(prefix = "tio.core.cluster")
public class TioServerClusterProperties {

    private final static String REDIS_CONFIG_PREFIX = "tio.core.cluster.redis";
    /**
     * 是否开启集群 默认为 false
     * */
    private boolean enabled = false;
    /**
     * 群组是否集群（同一个群组是否会分布在不同的机器上），false:不集群，默认不集群
     */
    private boolean	group = false;
    /**
     * 用户是否集群（同一个用户是否会分布在不同的机器上），false:不集群，默认集群
     */
    private boolean	user = true;
    /**
     * ip是否集群（同一个ip是否会分布在不同的机器上），false:不集群，默认集群
     */
    private boolean	ip = true;
    /**
     * id是否集群（在A机器上的客户端是否可以通过channelId发消息给B机器上的客户端），false:不集群，默认集群<br>
     */
    private boolean	channel	= true;
    /**
     * 所有连接是否集群（同一个ip是否会分布在不同的机器上），false:不集群，默认集群
     */
    private boolean	all = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isIp() {
        return ip;
    }

    public void setIp(boolean ip) {
        this.ip = ip;
    }

    public boolean isChannel() {
        return channel;
    }

    public void setChannel(boolean channel) {
        this.channel = channel;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean bll) {
        this.all = bll;
    }

    @ConfigurationProperties(REDIS_CONFIG_PREFIX)
    public static class RedisConfig {

        private String ip = "127.0.0.1";
        private Integer port = 6379;
        private String password;
        private Integer poolSize = 32;
        private Integer minimumIdleSize=16;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public boolean hasPassword(){
            return StrUtil.isNotBlank(getPassword());
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(Integer poolSize) {
            this.poolSize = poolSize;
        }

        public Integer getMinimumIdleSize() {
            return minimumIdleSize;
        }

        public void setMinimumIdleSize(Integer minimumIdleSize) {
            this.minimumIdleSize = minimumIdleSize;
        }

        @Override
        public String toString() {
            return "redis://" + getIp() + ":" + getPort();
        }
    }
}
