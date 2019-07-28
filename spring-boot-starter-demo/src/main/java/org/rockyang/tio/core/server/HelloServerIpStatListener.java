package org.rockyang.tio.core.server;

import org.tio.common.starter.annotation.TioServerIpStatListener;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;

/**
 * IP 统计监听，通过加 {@link TioServerIpStatListener} 注解启用，否则不会启用
 *
 * @author yangjian
 */
@TioServerIpStatListener
public class HelloServerIpStatListener implements IpStatListener {
    @Override
    public void onExpired(GroupContext groupContext, IpStat ipStat) {

    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1, IpStat ipStat) throws Exception {

    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i, IpStat ipStat) throws Exception {
        System.out.println("ipStat : " + ipStat.getIp());
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long l) throws Exception {

    }
}
