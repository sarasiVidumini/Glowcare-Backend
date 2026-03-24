package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.UserProfilesDTO;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserProfilesDTO> getAllUserProfiles() {
        return userRepository.findAll().stream()
                .map(user -> UserProfilesDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .status("Active") // You can add logic for this later
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateUserProfile(UserProfilesDTO userProfilesDTO) {

        User existingUser = userRepository.findById(userProfilesDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userProfilesDTO.getId()));


        existingUser.setName(userProfilesDTO.getName());
        existingUser.setEmail(userProfilesDTO.getEmail());


        if (userProfilesDTO.getRole() != null) {
            try {
                existingUser.setRole(lk.ijse.glowcare_backend.entity.Role.valueOf(userProfilesDTO.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Role: " + userProfilesDTO.getRole());
            }
        }

        userRepository.save(existingUser);

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
