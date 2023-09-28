package com.example.socket.config;

import com.example.socket.chat.ChatMessage;
import com.example.socket.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.simp.SimpMessageSendingOperations;


@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void WebSocketDisconnectListener(
        SessionDisconnectEvent event
    ){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username= (String) headerAccessor.getSessionAttributes().get("username");
        if (username!=null){
            log.info("User disconnected", username);
            var chatMessage= ChatMessage.builder()
                    .type(MessageType.LEAVER) //has left
                    .sender(username)
                    .build();
//inform all users
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
