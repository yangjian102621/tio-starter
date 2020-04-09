package org.rockyang.tio.core.server;

import org.rockyang.tio.common.starter.annotation.TioGroupListener;
import org.rockyang.tio.websocket.starter.listener.WsGroupListener;
import org.tio.core.ChannelContext;

/**
 * Tio group 监听，需要实现 {@link WsGroupListener} 接口
 * 通过加 {@link TioGroupListener} 注解启用，否则不会启用
 *
 * @author yangjian
 */
@TioGroupListener
public class WsServerGroupListener implements WsGroupListener {
    @Override
    public void onAfterBind(ChannelContext channelContext, String s) throws Exception {

    }

    @Override
    public void onAfterUnbind(ChannelContext channelContext, String s) throws Exception {

    }
}
