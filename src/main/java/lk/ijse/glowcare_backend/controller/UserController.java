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

    // 1. GET ALL PROFILES
    @GetMapping
    public ResponseEntity<List<UserProfilesDTO>> getAllUserProfiles() {
        List<UserProfilesDTO> profiles = userService.getAllUserProfiles();
        return ResponseEntity.ok(profiles);
    }

    // 2. UPDATE PROFILE
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserProfilesDTO userProfilesDTO) {


        userProfilesDTO.setId(id);

        userService.updateUserProfile(userProfilesDTO);
        return ResponseEntity.ok("User Profile Updated Successfully");
    }

    // 3. DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted Successfully");
    }
}