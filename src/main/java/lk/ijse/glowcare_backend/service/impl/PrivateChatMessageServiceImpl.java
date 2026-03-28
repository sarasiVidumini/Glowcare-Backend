package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.PrivateChatMessageDTO;
import lk.ijse.glowcare_backend.entity.PrivateChatMessage;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.PrivateChatMessageRepository;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.PrivateChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivateChatMessageServiceImpl implements PrivateChatMessageService {

    private final PrivateChatMessageRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public PrivateChatMessageDTO saveMessage(PrivateChatMessageDTO dto) {
        if (dto.getSenderId() == null || dto.getReceiverId() == null) {
            System.err.println("🛑 [CHAT ERROR]: Missing Sender or Receiver ID. Dropping message.");
            return null;
        }

        User sender = userRepository.findById(dto.getSenderId()).orElse(null);
        User receiver = userRepository.findById(dto.getReceiverId()).orElse(null);

        if (sender == null || receiver == null) {
            System.err.println("🛑 [CHAT ERROR]: Sender " + dto.getSenderId() + " or Receiver " + dto.getReceiverId() + " missing from DB.");
            return null;
        }

        PrivateChatMessage.MessageType msgType = dto.getType() != null ?
                PrivateChatMessage.MessageType.valueOf(dto.getType().name()) :
                PrivateChatMessage.MessageType.CHAT;

        PrivateChatMessage entity = PrivateChatMessage.builder()
                .roomId(dto.getRoomId())
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent() != null ? dto.getContent() : "")
                .fileUrl(dto.getFileUrl())
                .type(msgType)
                .isEdited(false)
                .build();

        return mapToDTO(chatRepository.save(entity));
    }

    @Override
    public PrivateChatMessageDTO editMessage(Long id, String newContent) {
        PrivateChatMessage msg = chatRepository.findById(id).orElse(null);
        if (msg == null) return null;
        msg.setContent(newContent);
        msg.setIsEdited(true);
        msg.setType(PrivateChatMessage.MessageType.EDIT);
        return mapToDTO(chatRepository.save(msg));
    }

    @Override
    public void deleteMessage(Long id) {
        if (chatRepository.existsById(id)) chatRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivateChatMessageDTO> getChatHistory(String roomId) {
        return chatRepository.findByRoomIdOrderByTimestampAsc(roomId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PrivateChatMessageDTO mapToDTO(PrivateChatMessage entity) {
        return PrivateChatMessageDTO.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .senderId(entity.getSender().getId())
                .receiverId(entity.getReceiver().getId())
                .senderName(entity.getSender().getName())
                .role(entity.getSender().getRole() != null ? entity.getSender().getRole().name() : "USER")
                .content(entity.getContent())
                .fileUrl(entity.getFileUrl())
                .isEdited(entity.getIsEdited())
                .type(PrivateChatMessageDTO.MessageType.valueOf(entity.getType().name()))
                .timestamp(entity.getTimestamp())
                .build();
    }
}