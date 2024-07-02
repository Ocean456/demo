package com.example.demo.handle;

import com.example.demo.dto.MessageDTO;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class SocketHandler extends TextWebSocketHandler {


    private static final Map<String, WebSocketSession> sessionMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        String token = Objects.requireNonNull(session.getUri()).getQuery().split("=")[1];
        logger.info(STR."Token: \{token}");
        String username = JWTUtil.parseJWT(token);
        if (username != null) {
            sessionMap.put(username, session);
            logger.info(STR."Session established: \{session.getId()}");
        } else {
            session.close();
        }

/*        for (Map.Entry<String, WebSocketSession> sessionEntry : sessionMap.entrySet()) {
            logger.info(STR."Session: \{sessionEntry.getKey()} \{sessionEntry.getValue().getId()}");
        }*/

        sessionMap.put(session.getId(), session);
//        session.sendMessage(new TextMessage(session.getId()));
        logger.info(STR."Session established: \{session.getId()}");

    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        // logger.info("Session: " + session.getId() + " Message: " + message.getPayload());

        /*JsonObject jsonObject = JsonParser.parseString(message.getPayload()).getAsJsonObject();

        String receiver = jsonObject.get("receiver").getAsString();
        String content = jsonObject.get("content").getAsString();


        // send message to receiver
        WebSocketSession receiverSession = sessionMap.get(receiver);
        if (receiverSession != null && receiverSession.isOpen()) {
            try {
                receiverSession.sendMessage(new TextMessage(content));
            } catch (IOException e) {
                logger.error("Error in sending message to receiver: " + receiver);
            }
        }*/
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessionMap.remove(session.getId());
        logger.info(STR."Session closed: \{session.getId()}");
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        logger.error(STR."Error in session: \{session.getId()}");
    }

    public static void sendMessageToUser(MessageDTO messageDTO) {
        WebSocketSession receiverSession = sessionMap.get(messageDTO.getReceiver());
        if (receiverSession != null && receiverSession.isOpen()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String message = mapper.writeValueAsString(messageDTO);
                receiverSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                logger.error(STR."Error in sending message to receiver: \{messageDTO.receiver}");
            }
        }
    }

    public static boolean isUserOnline(String username) {
        return sessionMap.containsKey(username);
    }

}
