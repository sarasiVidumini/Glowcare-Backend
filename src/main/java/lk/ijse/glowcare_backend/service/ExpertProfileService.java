package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.ExpertDTO;

import java.util.List;

public interface ExpertProfileService {

    List<ExpertDTO> getAllExperts();
    ExpertDTO getExpertById(Long id);
    void updateExpert(ExpertDTO expertDTO);
    void deleteExpert(Long id);
    ExpertDTO createExpert(ExpertDTO expertDTO);

}
