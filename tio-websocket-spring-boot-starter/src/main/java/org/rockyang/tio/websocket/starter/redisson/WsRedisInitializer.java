package org.rockyang.tio.websocket.starter.redisson;

import org.rockyang.tio.common.starter.RedisInitializer;
import org.rockyang.tio.common.starter.configuration.TioRedisClusterProperties;
import org.springframework.context.ApplicationContext;

/**
 * @author yangjian
 */
public class WsRedisInitializer extends RedisInitializer
{
	public WsRedisInitializer(TioRedisClusterProperties redisConfig, ApplicationContext applicationContext)
	{
		super(redisConfig, applicationContext);
	}
}
