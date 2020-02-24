package org.rockyang.tio.core.server.controller;

import org.rockyang.tio.websocket.starter.TioWebSocketServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

/**
 * websocket controller
 * @author yangjian
 */
@Controller
public class WsController {

	static Logger logger = LoggerFactory.getLogger(WsController.class);

	@Autowired
	private TioWebSocketServerBootstrap bootstrap;

	@GetMapping("/")
	public String index()
	{
		return "index";
	}

	/**
	 * 推送消息到客户端
	 * @throws Exception
	 */
	@GetMapping("/push")
	@ResponseBody
	public String pushMessage(String message)
	{
		message = "服务端主动推送的消息: " + message;

		Tio.sendToAll(bootstrap.getServerGroupContext(), WsResponse.fromText(message,"utf-8"));
		logger.info("Push a message to client successfully");
		return "Push a message to client successfully";
	}
}
