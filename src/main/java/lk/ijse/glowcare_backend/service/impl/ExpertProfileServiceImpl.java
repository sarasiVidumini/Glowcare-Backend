package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.ExpertDTO;
import lk.ijse.glowcare_backend.entity.ExpertProfile;
import lk.ijse.glowcare_backend.entity.Role;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.ExpertProfileRepository;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.ExpertProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpertProfileServiceImpl implements ExpertProfileService {

    private final ExpertProfileRepository expertRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExpertDTO> getAllExperts() {
        return expertRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExpertDTO getExpertById(Long id) {
        return expertRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Expert profile not found with id: " + id));
    }

    @Override
    public ExpertDTO createExpert(ExpertDTO expertDTO) {
        // 1. Create the User (Matches 'Full Name', 'Email', and 'Security Key')
        User newUser = User.builder()
                .name(expertDTO.getFullName())
                .email(expertDTO.getEmail())
                .password(expertDTO.getPassword()) // UI: Security Key
                .role(Role.EXPERT)
                .build();

        User savedUser = userRepository.save(newUser);

        // 2. Create the Profile (Matches 'License No')
        ExpertProfile newProfile = ExpertProfile.builder()
                .user(savedUser)
                .licenseNumber(expertDTO.getLicenseNumber())
                .expertiseArea("Clinical Expert") // Default or from DTO
                .bio("Premium Clinical Access Member") // Default or from DTO
                .build();

        return mapToDTO(expertRepository.save(newProfile));
    }

    @Override
    public void updateExpert(ExpertDTO expertDTO) {
        ExpertProfile existingExpert = expertRepository.findById(expertDTO.getId())
                .orElseThrow(() -> new RuntimeException("Expert not found with id: " + expertDTO.getId()));

        existingExpert.setLicenseNumber(expertDTO.getLicenseNumber());
        existingExpert.setExpertiseArea(expertDTO.getExpertiseArea()); // Uncommented!
        existingExpert.setBio(expertDTO.getBio());                     // Uncommented!

        if (existingExpert.getUser() != null) {
            existingExpert.getUser().setName(expertDTO.getFullName());
            existingExpert.getUser().setEmail(expertDTO.getEmail());
        }

        expertRepository.save(existingExpert);
    }

    @Override
    public void deleteExpert(Long id) {
        if (!expertRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Expert profile not found");
        }
        expertRepository.deleteById(id);
    }

    private ExpertDTO mapToDTO(ExpertProfile expert) {
        return ExpertDTO.builder()
                .id(expert.getId())
                .fullName(expert.getUser() != null ? expert.getUser().getName() : "Unknown")
                .email(expert.getUser() != null ? expert.getUser().getEmail() : "N/A")
                .licenseNumber(expert.getLicenseNumber())
                .expertiseArea(expert.getExpertiseArea()) // Uncommented!
                .bio(expert.getBio())                     // Uncommented!
                .build();
    }
}