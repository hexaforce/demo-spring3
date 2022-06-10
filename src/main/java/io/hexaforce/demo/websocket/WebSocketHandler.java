package io.hexaforce.demo.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends AbstractWebSocketHandler {

	final Map<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap<>();

	String getUserName(WebSocketSession session) {
		String[] path = session.getUri().getPath().split("/");
		return path[path.length - 1];
	}

	@Override
	protected void openSession(WebSocketSession session) throws IOException, RuntimeException {
		webSocketSessions.put(getUserName(session), session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message, String text) throws Exception {
		log.debug(text);
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message, byte[] binary) throws Exception {
		try {
			session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Text messages not supported"));
		} catch (IOException ex) {
			// ignore
		}
	}

	@Override
	protected void closeSession(WebSocketSession session) {
		webSocketSessions.remove(getUserName(session), session);
	}

}
