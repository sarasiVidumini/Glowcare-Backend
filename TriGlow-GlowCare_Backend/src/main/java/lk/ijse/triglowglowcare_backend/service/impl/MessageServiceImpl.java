package lk.ijse.triglowglowcare_backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.triglowglowcare_backend.dto.MessageRequestDTO;
import lk.ijse.triglowglowcare_backend.dto.MessageResponseDTO;
import lk.ijse.triglowglowcare_backend.entity.Chat_Type;
import lk.ijse.triglowglowcare_backend.entity.Message;
import lk.ijse.triglowglowcare_backend.repository.MessageRepo;
import lk.ijse.triglowglowcare_backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;
    private final ModelMapper modelMapper;


    @Override
    public MessageResponseDTO sendPublicMessage(String content, String sender) {
        Message msg = Message.builder()
                .senderEmail(sender)
                .receiverId(Long.valueOf("GLOBAL"))
                .chat_type(Chat_Type.PUBLIC)
                .content(content)
                .build();
        return modelMapper.map(messageRepo.save(msg), MessageResponseDTO.class);
    }

    @Override
    public MessageResponseDTO sendPrivateMessage(String content, String sender, Long expertId) {
        Message msg = Message.builder()
                .senderEmail(sender)
                .receiverId(expertId)
                .chat_type(Chat_Type.PRIVATE_EXPERT)
                .content(content)
                .build();
        return modelMapper.map(messageRepo.save(msg), MessageResponseDTO.class);
    }

    @Override
    public MessageResponseDTO editMessage(Long id, String newContent, String currentUserEmail) {
        Message message = messageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + id));

        // Security Check: Only the owner can edit
        validateOwnership(message, currentUserEmail);

        message.setContent(newContent);
        // @UpdateTimestamp in Entity handles the edited time automatically
        return modelMapper.map(messageRepo.save(message), MessageResponseDTO.class);
    }

    private void validateOwnership(Message message, String currentUserEmail) {
        if (!message.getSenderEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Access Denied: You do not own this message.");
        }
    }


    @Override
    public void deleteMessage(Long id, String currentUserEmail) {
        Message message = messageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + id));

        // Security Check: Only the owner can delete
        validateOwnership(message, currentUserEmail);

        messageRepo.delete(message);
    }

    @Override
    @Transactional
    public List<MessageResponseDTO> getPublicMessages() {
        return messageRepo.findByChatTypeOrderByTimestampAsc(Chat_Type.PUBLIC)
                .stream()
                .map(msg -> modelMapper.map(msg, MessageResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MessageResponseDTO> getPrivateMessages(String userEmail, Long expertId) {
        return messageRepo.findPrivateConversation(userEmail, expertId)
                .stream()
                .map(msg -> modelMapper.map(msg, MessageResponseDTO.class))
                .collect(Collectors.toList());
    }
}
