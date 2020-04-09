package org.rockyang.tio.core.server;

import org.rockyang.tio.common.starter.annotation.TioAioListener;
import org.rockyang.tio.core.starter.listener.SocketServerAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

/**
 * 消息监听，需要实现 {@link ServerAioListener} 接口
 * 通过加 {@link TioAioListener} 注解启用，否则不会启用
 * Note: Bean 的名称不能改动，否则无法注入
 *
 * @author yangjian
 */
@TioAioListener
public class HelloServerAioListener implements SocketServerAioListener {


    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        System.out.println("FUCK");
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {

    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {

    }
}
