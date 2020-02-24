package org.rockyang.tio.core.server;

import org.rockyang.tio.common.starter.TioServerMsgHandlerNotFoundException;
import org.rockyang.tio.common.starter.annotation.TioMsgHandler;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * 和 Tio WebSocket 用法一致，需要实现 {@link IWsMsgHandler} 接口，
 * 通过加 {@link TioMsgHandler} 注解启用
 * 注意: handler 是必须要启用的，否则启动会抛出 {@link TioServerMsgHandlerNotFoundException} 异常
 *
 * @author yangjian
 */
@TioMsgHandler
public class WsServerMsgHandler implements IWsMsgHandler {


    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception
    {
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception
    {

    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception
    {
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception
    {
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String message, ChannelContext channelContext) throws Exception
    {
        System.out.println("收到来自客户端的消息：" + message);
        // 回复消息
        Tio.sendToAll(channelContext.getGroupContext(), WsResponse.fromText("服务端收到了你的消息，你的消息是：" + message,"utf-8"));
        return null;
    }
}
