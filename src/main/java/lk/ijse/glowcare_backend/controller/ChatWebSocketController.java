package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.PublicChatMessageDTO;
import lk.ijse.glowcare_backend.service.PublicChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final PublicChatMessageService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public PublicChatMessageDTO sendMessage(@Payload PublicChatMessageDTO chatMessage) {
        System.out.println("💬 [WEBSOCKET] Received message from user ID: " + chatMessage.getUserId());
        try {
            return chatService.saveMessage(chatMessage);
        } catch (Exception e) {
            System.err.println("❌ [WEBSOCKET CRASH] Failed to save message:");
            e.printStackTrace();
            return null;
        }
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public PublicChatMessageDTO addUser(@Payload PublicChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
        try {
            return chatService.saveMessage(chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @MessageMapping("/chat.editMessage")
    @SendTo("/topic/public")
    public PublicChatMessageDTO editMessage(@Payload PublicChatMessageDTO chatMessage) {
        try {
            return chatService.editMessage(chatMessage.getId(), chatMessage.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @MessageMapping("/chat.deleteMessage")
    @SendTo("/topic/public")
    public PublicChatMessageDTO deleteMessage(@Payload PublicChatMessageDTO chatMessage) {
        try {
            chatService.deleteMessage(chatMessage.getId());
            return chatMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}