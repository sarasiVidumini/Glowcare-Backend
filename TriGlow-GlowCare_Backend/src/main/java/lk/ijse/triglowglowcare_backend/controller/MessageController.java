package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.MessageRequestDTO;
import lk.ijse.triglowglowcare_backend.dto.MessageResponseDTO;
import lk.ijse.triglowglowcare_backend.service.MessageService;
import lk.ijse.triglowglowcare_backend.service.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class MessageController {

    private final MessageServiceImpl messageServiceImpl;

    /**
     * Requirement: Anyone who has joined can post in public chat.
     * Requirement: Every user should see the message posted.
     */
    @PostMapping("/public")
    public ResponseEntity<MessageResponseDTO> sendPublicMessage(@RequestBody MessageRequestDTO requestDTO) {
        MessageResponseDTO response = messageServiceImpl.sendPublicMessage(
                requestDTO.getContent(),
                requestDTO.getSenderEmail()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Requirement: Fetch all messages for the public chat.
     */
    @GetMapping("/public")
    public ResponseEntity<List<MessageResponseDTO>> getPublicMessages() {
        return ResponseEntity.ok(messageServiceImpl.getPublicMessages());
    }

    /**
     * Requirement: Send private messages ONLY to that relevant user and expert.
     */
    @PostMapping("/private")
    public ResponseEntity<MessageResponseDTO> sendPrivateMessage(@RequestBody MessageRequestDTO requestDTO) {
        MessageResponseDTO response = messageServiceImpl.sendPrivateMessage(
                requestDTO.getContent(),
                requestDTO.getSenderEmail(),
                requestDTO.getReceiverId() // Maps to expertId
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Requirement: Private history for the relevant pair.
     */
    @GetMapping("/private/{userEmail}/{expertId}")
    public ResponseEntity<List<MessageResponseDTO>> getPrivateMessages(
            @PathVariable String userEmail,
            @PathVariable Long expertId) {
        return ResponseEntity.ok(messageServiceImpl.getPrivateMessages(userEmail, expertId));
    }

    /**
     * Requirement: Security check - only owner can edit.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> editMessage(
            @PathVariable Long id,
            @RequestParam String newContent,
            @RequestParam String currentUserEmail) {
        return ResponseEntity.ok(messageServiceImpl.editMessage(id, newContent, currentUserEmail));
    }

    /**
     * Requirement: Security check - only owner can delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long id,
            @RequestParam String currentUserEmail) {
        messageServiceImpl.deleteMessage(id, currentUserEmail);
        return ResponseEntity.noContent().build();
    }
}
