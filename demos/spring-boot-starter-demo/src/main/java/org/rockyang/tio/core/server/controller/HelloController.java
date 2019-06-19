package org.rockyang.tio.core.server.controller;

import org.rockyang.tio.core.server.HelloPacket;
import org.rockyang.tio.core.starter.TioServerBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;

/**
 * @author yangjian
 */
@RestController
public class HelloController {

	@Autowired
	private TioServerBootstrap bootstrap;

	@GetMapping("/")
	public String index()
	{
		return "Hello, Tio";
	}

	/**
	 * 推送消息到客户端
	 * @throws Exception
	 */
	@GetMapping("/push")
	public void pushMessage() throws Exception {
		HelloPacket packet = new HelloPacket();
		packet.setBody("This message is pushed by Tio Server.".getBytes(HelloPacket.CHARSET));
		Tio.sendToAll(bootstrap.getServerGroupContext(), packet);
	}
}
