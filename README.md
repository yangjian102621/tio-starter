# Tio-Starter
Tio 通用 TCP 服务的 spring-boot-starter 实现

# 项目地址
* Github : https://github.com/yangjian102621/tio-starter
* Gitee: https://gitee.com/blackfox/tio-starter

# 快速开始

首先，你需要在 pom.xml 中引入 `tio-core-spring-boot-starter` 构件

```xml
<dependency>
	<groupId>org.t-io</groupId>
	<artifactId>tio-core-spring-boot-starter</artifactId>
	<!--此版本号跟着tio主版本号一致即可-->
   <version>3.3.5.v20190712-RELEASE</version>
</dependency>
```

# 编写服务端程序

一、给SpringBoot Application 主类添加 `@EnableTioServerServer` 注解

```java
@SpringBootApplication
@EnableTioServerServer
public class TioServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TioServerApplication.class, args);
    }
}
```

二、接下来，修改配置文件

```yaml
tio:
  core:
    server:
      # websocket port default 9876
      port: 6789
      # 心跳时间
      heartbeat-timeout: 60000
      # 监控时段,多个之间用逗号隔开
      ip-stat-durations: [60]
      # 集群配置 默认关闭
    cluster:
      enabled: false
      # 集群是通过redis的Pub/Sub实现，所以需要配置Redis
      redis:
        ip: 127.0.0.1
        port: 6379
      all: true
      group: true
      ip: true
      user: true
      # SSL 配置
      ssl:
        enabled: false
        key-store:
        password:
        trust-store:
```

三、编写消息处理类

```java
/**
 * 消息处理 handler, 通过加 {@link TioServerMsgHandler} 注解启用，否则不会启用
 * 注意: handler 是必须要启用的，否则启动会抛出 {@link TioMsgHandlerNotFoundException} 异常
 *
 * @author yangjian
 */
@TioServerMsgHandler
public class HelloServerMsgHandler implements ServerAioHandler {


    /**
     * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @Override
    public HelloPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException
    {
        return PacketUtil.decode(buffer, limit, position, readableLength, channelContext);
    }

    /**
     * 编码：把业务消息包编码为可以发送的ByteBuffer
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext)
    {
        return PacketUtil.encode(packet, groupContext, channelContext);
    }


    /**
     * 处理消息
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        HelloPacket helloPacket = (HelloPacket) packet;
        byte[] body = helloPacket.getBody();
        if (body != null) {
            String str = new String(body, HelloPacket.CHARSET);
            System.out.println("收到消息：" + str);

            HelloPacket resppacket = new HelloPacket();
            resppacket.setBody(("收到了你的消息，你的消息是:" + str).getBytes(HelloPacket.CHARSET));
            Tio.send(channelContext, resppacket);
        }
        return;
    }
}
```

四、实现消息实体包

```java
/**
 * 消息包实体
 *
 * @author yangjian
 */
public class HelloPacket extends Packet {
	private static final long serialVersionUID = -172060606924066412L;
	public static final int HEADER_LENGTH = 4;//消息头的长度
	public static final String CHARSET = "utf-8";
	private byte[] body;

	/**
	 * @return the body
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}
}
```

接下来启动服务端

# 编写客户端程序
客户端采用 Tio 的常规程序启动，只有三个文件，启动非常简单。

一、编写常量类

```java
public interface Const {
	/**
	 * 服务器地址
	 */
	public static final String SERVER = "127.0.0.1";
	
	/**
	 * 监听端口
	 */
	public static final int PORT = 6789;

	/**
	 * 心跳超时时间
	 */
	public static final int TIMEOUT = 5000;
}
```

二、消息处理类

```java
public class HelloClientAioHandler implements ClientAioHandler {
	private static HelloPacket heartbeatPacket = new HelloPacket();


	/**
	 * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
	 * 总的消息结构：消息头 + 消息体
	 * 消息头结构：    4个字节，存储消息体的长度
	 * 消息体结构：   对象的json串的byte[]
	 */
	@Override
	public HelloPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException
	{
		return PacketUtil.decode(buffer, limit, position, readableLength, channelContext);
	}

	/**
	 * 编码：把业务消息包编码为可以发送的ByteBuffer
	 * 总的消息结构：消息头 + 消息体
	 * 消息头结构：    4个字节，存储消息体的长度
	 * 消息体结构：   对象的json串的byte[]
	 */
	@Override
	public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext)
	{
		return PacketUtil.encode(packet, groupContext, channelContext);
	}
	
	/**
	 * 处理消息
	 */
	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		HelloPacket helloPacket = (HelloPacket) packet;
		byte[] body = helloPacket.getBody();
		if (body != null) {
			String str = new String(body, HelloPacket.CHARSET);
			System.out.println("收到消息：" + str);
		}

		return;
	}

	/**
	 * 此方法如果返回null，框架层面则不会发心跳；如果返回非null，框架层面会定时发本方法返回的消息包
	 */
	@Override
	public HelloPacket heartbeatPacket(ChannelContext channelContext) {
		return heartbeatPacket;
	}
```

三、客户端启动类

```java
public class HelloClientStarter {
	//服务器节点
	public static Node serverNode = new Node(Const.SERVER, Const.PORT);

	//handler, 包括编码、解码、消息处理
	public static ClientAioHandler tioClientHandler = new HelloClientAioHandler();

	//事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
	public static ClientAioListener aioListener = null;

	//断链后自动连接的，不想自动连接请设为null
	private static ReconnConf reconnConf = new ReconnConf(5000L);

	//一组连接共用的上下文对象
	public static ClientGroupContext clientGroupContext = new ClientGroupContext(tioClientHandler, aioListener, reconnConf);

	public static TioClient tioClient = null;
	public static ClientChannelContext clientChannelContext = null;

	/**
	 * 启动程序入口
	 */
	public static void main(String[] args) throws Exception {
		clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
		tioClient = new TioClient(clientGroupContext);
		clientChannelContext = tioClient.connect(serverNode);
		//连上后，发条消息玩玩
		send();
	}

	private static void send() throws Exception {
		HelloPacket packet = new HelloPacket();
		packet.setBody("hello world".getBytes(HelloPacket.CHARSET));
		Tio.send(clientChannelContext, packet);
	}
}
```

启动客户端端，查看终端输出。

> 服务端输出

![](http://blog.img.r9it.com/image-808ac7fef58cfd75eb4d9e053af9c4a6.png)


# 原生回调接口支持

跟 handler 一样，其他原生回调接口的使用方法保持不变，只需要在对应的实现类上加上对应的注解就 OK 了。

```java
//最主要的逻辑处理类，必须要写，否则抛异常
public class HelloServerMsgHandler implements ServerAioHandler {}
//可不写，通过加 @TioServerAioListener 注解启用，否则不会启用
public class HelloServerAioListener implements ServerAioListener {}
//可不写， 通过加 @TioServerGroupListener 注解启用，否则不会启用
public class HelloServerGroupListener implements GroupListener{}
//可不写，通过加 @link TioServerIpStatListener 注解启用，否则不会启用
public class HelloServerIpStatListener implements IpStatListener {}
```
**这里注意：每个对应的回调接口都需要通过添加注解手动启用，否则默认不启用，不会自动扫描**

# 服务端主动推送

这个也非常简单，只需获取到 `TioServerBootstrap` ，其他都变得非常简单。

```java
@RestController
public class HelloController {

	static Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private TioServerBootstrap bootstrap;

	@GetMapping("/")
	public String index()
	{
		return "Hello, tio-spring-boot-starter !!!";
	}

	/**
	 * 推送消息到客户端
	 * @throws Exception
	 */
	@GetMapping("/push")
	public String pushMessage() throws Exception {
		HelloPacket packet = new HelloPacket();
		packet.setBody("This message is pushed by Tio Server.".getBytes(HelloPacket.CHARSET));
		Tio.sendToAll(bootstrap.getServerGroupContext(), packet);
		logger.info("Push a message to client successfully");
		return "Push a message to client successfully";
	}
}
```

> 客户端输出截图

![](http://blog.img.r9it.com/image-404c49a83dd7ff0a5f2329c534cafc65.png)

# SSL 支持

```yaml
# SSL 配置
    ssl:
      enabled: true
      key-store: key-store path
      password: password
      trust-store: trust-store path
```
# 集群支持

```yaml
# 集群配置 默认关闭
    cluster:
      enabled: false
      # 集群是通过redis的Pub/Sub实现，所以需要配置Redis
      redis:
        ip: 127.0.0.1
        port: 6379
      all: true
      group: true
      ip: true
      user: true

```

### 完整功能请参考本项目中的 `spring-boot-starter-demo`