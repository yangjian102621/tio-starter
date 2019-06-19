package org.rockyang.tio.core.starter.annotation;

import org.rockyang.tio.core.starter.handler.TioServerAioHandler;
import org.springframework.stereotype.Service;

/**
 * Tio Server message handler 注解，用来创建 {@link TioServerAioHandler}
 * @author yangjian
 * */
@Service
public @interface TioServerMsgHandler {
}
