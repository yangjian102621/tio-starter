package org.rockyang.tio.common.starter.annotation;

import org.springframework.stereotype.Service;

/**
 * 此注解用于启用 Tio group 消息监听
 * @author yangjian
 */
@Service
public @interface TioGroupListener
{
	String[] name() default {};
}
