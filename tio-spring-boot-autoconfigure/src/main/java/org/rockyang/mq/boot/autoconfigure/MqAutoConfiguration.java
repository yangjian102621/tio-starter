package org.rockyang.mq.boot.autoconfigure;

import org.apache.rocketmq.client.exception.MQClientException;
import org.rockyang.mq.MessageConsumer;
import org.rockyang.mq.MessageProducer;
import org.rockyang.mq.rocketmq.RocketMqConsumer;
import org.rockyang.mq.rocketmq.RocketMqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzhaoju
 * @author yangjian
 */
@Configuration
@EnableConfigurationProperties(MqProperties.class)
public class MqAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MqAutoConfiguration.class);

    private final MqProperties properties;


    public MqAutoConfiguration(MqProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageProducer messageProducer() throws Exception {
        if(null != this.properties && null != this.properties.getProducerGroup()){
            logger.info("初始化MessageProducer,{}",this.properties.getProducerGroup());
            MessageProducer producer = new RocketMqProducer(this.properties.getNameservAddr() , this.properties.getProducerGroup());
            return producer;
        }
        return null;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageConsumer messageConsumer() throws MQClientException {
        if(null != this.properties && null != this.properties.getConsumerGroup()){
            logger.info("初始化MessageConsumer,{}",this.properties.getConsumerGroup());
            MessageConsumer producer = new RocketMqConsumer(this.properties.getNameservAddr() , this.properties.getConsumerGroup());
            return producer;
        }
        return null;
    }

}
