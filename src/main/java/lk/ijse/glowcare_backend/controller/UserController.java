package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.UserProfilesDTO;
import lk.ijse.glowcare_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // ===============================
    // 1. GET ALL USER PROFILES
    // ===============================
    @GetMapping
    public ResponseEntity<List<UserProfilesDTO>> getAllUserProfiles() {

        List<UserProfilesDTO> profiles =
                userService.getAllUserProfiles();

        return ResponseEntity.ok(profiles);
    }

    // ===============================
    // 2. UPDATE USER PROFILE
    // ===============================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserProfilesDTO userProfilesDTO) {

        try {

            userProfilesDTO.setId(id);

            userService.updateUserProfile(userProfilesDTO);

            return ResponseEntity.ok("User Profile Updated Successfully");

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Update Failed: " + e.getMessage());
        }
    }

    // ===============================
    // 3. DELETE USER
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }

}