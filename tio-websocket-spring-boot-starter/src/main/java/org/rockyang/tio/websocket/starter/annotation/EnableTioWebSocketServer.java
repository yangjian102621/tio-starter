package org.rockyang.tio.websocket.starter.annotation;

import org.rockyang.tio.websocket.starter.TioWebSocketServerAutoConfiguration;
import org.rockyang.tio.websocket.starter.TioWebSocketServerMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation to activate Tio WebSocket Server related configuration {@link TioWebSocketServerAutoConfiguration}
 *
 * @author fanpan26
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TioWebSocketServerMarkerConfiguration.class)
public @interface EnableTioWebSocketServer {
}
