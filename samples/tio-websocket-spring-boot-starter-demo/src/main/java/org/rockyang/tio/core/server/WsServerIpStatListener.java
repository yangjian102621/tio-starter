package org.rockyang.tio.core.server;

import org.rockyang.tio.common.starter.annotation.TioIpStatListener;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;

/**
 * IP 统计监听，需要实现 {@link IpStatListener} 接口
 * 通过加 {@link TioIpStatListener} 注解启用，否则不会启用
 *
 * @author yangjian
 */
@TioIpStatListener(name = "wsIpStatListener")
public class WsServerIpStatListener implements IpStatListener {
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
