package io.hexaforce.demo.websocket;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public abstract class AbstractWebSocketHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		openSession(session);
	}

	abstract protected void openSession(WebSocketSession session) throws IOException, RuntimeException;

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getPayload();
			handleTextMessage(session, textMessage, text);
		} else if (message instanceof BinaryMessage) {
			BinaryMessage binaryMessage = (BinaryMessage) message;
			ByteBuffer byteBuffer = binaryMessage.getPayload();
			byteBuffer.position(0);
			handleBinaryMessage(session, binaryMessage, byteBuffer.array());
		} else if (message instanceof PongMessage) {
			handlePongMessage(session, (PongMessage) message);
		} else {
			throw new IllegalStateException("Unexpected WebSocket message type: " + message);
		}
	}

	abstract protected void handleTextMessage(WebSocketSession session, TextMessage message, String text) throws Exception;

	abstract protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message, byte[] binary) throws Exception;

	protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		out.println(message.getPayload().toString());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		err.println(exception.getMessage());
		exception.printStackTrace(err);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		out.println(status.toString());
		closeSession(session);
	}

	abstract protected void closeSession(WebSocketSession session);

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}