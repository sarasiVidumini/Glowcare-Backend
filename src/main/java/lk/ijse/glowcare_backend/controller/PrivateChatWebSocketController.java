package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.PrivateChatMessageDTO;
import lk.ijse.glowcare_backend.service.PrivateChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PrivateChatWebSocketController {

    private final PrivateChatMessageService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/private-chat.sendMessage")
    public void sendMessage(@Payload PrivateChatMessageDTO chatMessage) {
        try {
            PrivateChatMessageDTO savedMsg = chatService.saveMessage(chatMessage);
            if (savedMsg != null) {
                messagingTemplate.convertAndSend("/topic/private/" + chatMessage.getRoomId(), savedMsg);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @MessageMapping("/private-chat.addUser")
    public void addUser(@Payload PrivateChatMessageDTO chatMessage) {
        try {
            PrivateChatMessageDTO savedMsg = chatService.saveMessage(chatMessage);
            if (savedMsg != null) {
                messagingTemplate.convertAndSend("/topic/private/" + chatMessage.getRoomId(), savedMsg);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @MessageMapping("/private-chat.editMessage")
    public void editMessage(@Payload PrivateChatMessageDTO chatMessage) {
        try {
            PrivateChatMessageDTO editedMsg = chatService.editMessage(chatMessage.getId(), chatMessage.getContent());
            if (editedMsg != null) {
                messagingTemplate.convertAndSend("/topic/private/" + chatMessage.getRoomId(), editedMsg);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @MessageMapping("/private-chat.deleteMessage")
    public void deleteMessage(@Payload PrivateChatMessageDTO chatMessage) {
        try {
            chatService.deleteMessage(chatMessage.getId());
            messagingTemplate.convertAndSend("/topic/private/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) { e.printStackTrace(); }
    }
}