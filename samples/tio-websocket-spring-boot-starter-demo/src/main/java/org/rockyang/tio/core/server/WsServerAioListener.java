package org.rockyang.tio.core.server;

import org.rockyang.tio.common.starter.annotation.TioAioListener;

/**
 * 消息监听，需要继承 {@link org.tio.websocket.server.WsServerAioListener} 类
 * 通过加 {@link TioAioListener} 注解启用，否则不会启用
 *
 * @author yangjian
 */
@TioAioListener(name = "wsServerAioListener")
public class WsServerAioListener extends org.tio.websocket.server.WsServerAioListener {
}
