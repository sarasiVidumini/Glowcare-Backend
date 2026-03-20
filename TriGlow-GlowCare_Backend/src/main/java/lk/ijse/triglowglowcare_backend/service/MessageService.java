package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.MessageRequestDTO;
import lk.ijse.triglowglowcare_backend.dto.MessageResponseDTO;

import java.util.List;

public interface MessageService {

    MessageResponseDTO sendPublicMessage(String content, String sender);

    MessageResponseDTO sendPrivateMessage(String content, String sender, Long expertId);

    MessageResponseDTO editMessage(Long id, String newContent, String currentUserEmail);

    void deleteMessage(Long id, String currentUserEmail);

    List<MessageResponseDTO> getPublicMessages();

    List<MessageResponseDTO> getPrivateMessages(String userEmail, Long expertId);

}
