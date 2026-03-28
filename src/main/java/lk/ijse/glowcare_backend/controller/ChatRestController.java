package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.PublicChatMessageDTO;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.PublicChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChatRestController {

    private final PublicChatMessageService chatService;
    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/history")
    public ResponseEntity<List<PublicChatMessageDTO>> getChatHistory() {
        return ResponseEntity.ok(chatService.getRecentChatHistory());
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String name) {
        Optional<User> userOpt = userRepository.findFirstByNameIgnoreCase(name.trim());

        if (userOpt.isPresent()) {
            User u = userOpt.get();

            // 🚀 FIXED: Manually build the JSON so Hibernate cannot hide the ID!
            Map<String, Object> safeUserResponse = new HashMap<>();
            safeUserResponse.put("id", u.getId());
            safeUserResponse.put("name", u.getName());
            safeUserResponse.put("role", u.getRole() != null ? u.getRole().name() : "USER");

            return ResponseEntity.ok(safeUserResponse);
        }
        return ResponseEntity.badRequest().body("User not found in the system.");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) directory.mkdirs();

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = "http://localhost:8080/uploads/" + fileName;
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }
}