package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.PublicChatMessageDTO;
import lk.ijse.glowcare_backend.entity.PublicChatMessage;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.PublicChatMessageRepository;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.PublicChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PublicChatMessageServiceImpl implements PublicChatMessageService {

    private final PublicChatMessageRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public PublicChatMessageDTO saveMessage(PublicChatMessageDTO dto) {

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("CRITICAL: Frontend failed to send a User ID.");
        }

        final Long finalUserId = dto.getUserId();

        User user = userRepository.findById(finalUserId)
                .orElseThrow(() -> new RuntimeException("User ID " + finalUserId + " not found in DB!"));

        PublicChatMessage.MessageType msgType = dto.getType() != null ?
                PublicChatMessage.MessageType.valueOf(dto.getType().name()) :
                PublicChatMessage.MessageType.CHAT;

        PublicChatMessage entity = PublicChatMessage.builder()
                .user(user)
                .content(dto.getContent() != null ? dto.getContent() : "")
                .fileUrl(dto.getFileUrl())
                .type(msgType)
                .isEdited(false)
                .build();

        return mapToDTO(chatRepository.save(entity));
    }

    @Override
    public PublicChatMessageDTO editMessage(Long id, String newContent) {
        PublicChatMessage existingMessage = chatRepository.findById(id).orElseThrow();
        existingMessage.setContent(newContent);
        existingMessage.setEdited(true);
        existingMessage.setType(PublicChatMessage.MessageType.EDIT);
        return mapToDTO(chatRepository.save(existingMessage));
    }

    @Override
    public void deleteMessage(Long id) {
        if (chatRepository.existsById(id)) chatRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicChatMessageDTO> getRecentChatHistory() {
        List<PublicChatMessage> recentMessages = chatRepository.findTop100ByOrderByTimestampDesc();
        Collections.reverse(recentMessages); // Puts newest messages at the bottom
        return recentMessages.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PublicChatMessageDTO mapToDTO(PublicChatMessage entity) {
        return PublicChatMessageDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .senderName(entity.getUser().getName())
                // Extracts the exact database role (e.g., "CLIENT", "ADMIN")
                .role(entity.getUser().getRole() != null ? entity.getUser().getRole().name() : "USER")
                .content(entity.getContent())
                .fileUrl(entity.getFileUrl())
                .isEdited(entity.isEdited())
                .type(PublicChatMessageDTO.MessageType.valueOf(entity.getType().name()))
                .timestamp(entity.getTimestamp())
                .build();
    }
}