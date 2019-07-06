package org.rockyang.tio.core.server;

import org.tio.common.starter.annotation.TioServerGroupListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.GroupListener;

/**
 * Tio group 监听，通过加 {@link TioServerGroupListener} 注解启用，否则不会启用
 *
 * @author yangjian
 */
@TioServerGroupListener
public class HelloServerGroupListener implements GroupListener {
    @Override
    public void onAfterBind(ChannelContext channelContext, String s) throws Exception {

    }

    @Override
    public void onAfterUnbind(ChannelContext channelContext, String s) throws Exception {

    }
}
