package com.example.socket.chat;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class ChatController {
    //add user method

    @MessageMapping("/chat.addUser") // the URL you want to use
    @SendTo("/topic/public")// to which you want to send
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ){
        // add username in websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    //sendMessage method
    @MessageMapping("/chat.sendMessage") // the URL you want to use
    @SendTo("/topic/public")// to which you want to send
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ){

        return chatMessage;
    }
}
