package org.rockyang.tio.websocket.starter.configuration;

import org.rockyang.tio.common.starter.configuration.TioProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanpan26
 * */
@ConfigurationProperties("tio.websocket.server")
public class TioWebSocketServerProperties extends TioProperties {

	/**
	 * Snowflake workerId，用于产生分布式唯一 ID
	 */
	private long workerId = 1L;
	/**
	 * Snowflake dataCenter，用于产生分布式唯一 ID
	 */
	private long dataCenter = 1L;

	public long getWorkerId()
	{
		return workerId;
	}

	public void setWorkerId(long workerId)
	{
		this.workerId = workerId;
	}

	public long getDataCenter()
	{
		return dataCenter;
	}

	public void setDataCenter(long dataCenter)
	{
		this.dataCenter = dataCenter;
	}
}
