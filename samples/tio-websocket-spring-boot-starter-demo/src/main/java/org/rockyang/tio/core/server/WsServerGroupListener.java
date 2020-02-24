package org.rockyang.tio.core.server;

import org.rockyang.tio.common.starter.annotation.TioGroupListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.GroupListener;

/**
 * Tio group 监听，需要实现 {@link GroupListener} 接口
 * 通过加 {@link TioGroupListener} 注解启用，否则不会启用
 *
 * @author yangjian
 */
@TioGroupListener
public class WsServerGroupListener implements GroupListener {
    @Override
    public void onAfterBind(ChannelContext channelContext, String s) throws Exception {

    }

    @Override
    public void onAfterUnbind(ChannelContext channelContext, String s) throws Exception {

    }
}
