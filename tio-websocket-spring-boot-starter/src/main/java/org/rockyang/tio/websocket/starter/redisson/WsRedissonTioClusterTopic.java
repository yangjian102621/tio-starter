package org.rockyang.tio.websocket.starter.redisson;

import org.redisson.api.RedissonClient;
import org.tio.cluster.redisson.RedissonTioClusterTopic;

/**
 * @author yangjian
 */
public class WsRedissonTioClusterTopic extends RedissonTioClusterTopic
{

	public WsRedissonTioClusterTopic(String channel, RedissonClient redisson)
	{
		super(channel, redisson);
	}
}
