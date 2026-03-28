package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.PublicChatMessageDTO;
import java.util.List;

public interface PublicChatMessageService {

    PublicChatMessageDTO saveMessage(PublicChatMessageDTO messageDTO);
    PublicChatMessageDTO editMessage(Long id, String newContent);
    void deleteMessage(Long id);
    List<PublicChatMessageDTO> getRecentChatHistory();

}