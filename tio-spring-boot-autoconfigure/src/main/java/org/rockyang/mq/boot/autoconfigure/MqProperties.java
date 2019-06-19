package org.rockyang.mq.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * mybatis 配置
 *
 * @author chenzhaoju
 * @author yangjian
 */
@ConfigurationProperties(prefix = MqProperties.MQ_PREFIX)
public class MqProperties {

  public static final String MQ_PREFIX = "mq";

  /** nameserver 地址 */
  private String nameservAddr;
  /** producerGroup 名称 */
  private String producerGroup;
  /** consumerGroup 名称 */
  private String consumerGroup;

  public String getNameservAddr() {
    return nameservAddr;
  }

  public String getProducerGroup() {
    return producerGroup;
  }

  public String getConsumerGroup() {
    return consumerGroup;
  }

  public MqProperties setNameservAddr(String nameservAddr) {
    this.nameservAddr = nameservAddr;
    return this;
  }

  public MqProperties setProducerGroup(String producerGroup) {
    this.producerGroup = producerGroup;
    return this;
  }

  public MqProperties setConsumerGroup(String consumerGroup) {
    this.consumerGroup = consumerGroup;
    return this;
  }

  @Override
  public String toString() {
    return "MqProperties{" +
            "nameservAddr='" + nameservAddr + '\'' +
            ", producerGroup='" + producerGroup + '\'' +
            ", consumerGroup='" + consumerGroup + '\'' +
            '}';
  }
}
