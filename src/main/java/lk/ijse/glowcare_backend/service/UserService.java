package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.UserProfilesDTO;

import java.util.List;

public interface UserService {

    List<UserProfilesDTO> getAllUserProfiles();

    void updateUserProfile(UserProfilesDTO userProfilesDTO);

    void deleteUser(Long id);
}
