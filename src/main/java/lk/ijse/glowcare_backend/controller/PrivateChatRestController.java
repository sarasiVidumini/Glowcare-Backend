package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.PrivateChatMessageDTO;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.PrivateChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/v1/private-chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PrivateChatRestController {

    private final PrivateChatMessageService chatService;
    private final UserRepository userRepository;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/history")
    public ResponseEntity<List<PrivateChatMessageDTO>> getChatHistory(
            @RequestParam String roomId
    ) {
        return ResponseEntity.ok(chatService.getChatHistory(roomId));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(
            @RequestParam(required = false) String name
    ) {

        try {

            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Username is required.");
            }

            Optional<User> userOpt =
                    userRepository.findFirstByNameIgnoreCase(name.trim());

            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("User not found.");
            }

            User u = userOpt.get();

            Map<String, Object> response = new HashMap<>();

            response.put("id", u.getId());
            response.put("name", u.getName());
            response.put(
                    "role",
                    u.getRole() != null
                            ? u.getRole().name()
                            : "USER"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body("Verification failed.");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            File dir = new File(UPLOAD_DIR);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            Files.write(
                    Paths.get(UPLOAD_DIR + fileName),
                    file.getBytes()
            );

            return ResponseEntity.ok(
                    "http://localhost:8080/uploads/" + fileName
            );

        } catch (IOException e) {

            e.printStackTrace();

            return ResponseEntity.status(500)
                    .body("Upload failed");
        }
    }
}