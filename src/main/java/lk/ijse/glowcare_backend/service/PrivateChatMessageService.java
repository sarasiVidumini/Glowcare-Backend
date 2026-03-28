package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.PrivateChatMessageDTO;
import java.util.List;

public interface PrivateChatMessageService {
    PrivateChatMessageDTO saveMessage(PrivateChatMessageDTO dto);
    PrivateChatMessageDTO editMessage(Long id, String newContent);
    void deleteMessage(Long id);
    List<PrivateChatMessageDTO> getChatHistory(String roomId);
}