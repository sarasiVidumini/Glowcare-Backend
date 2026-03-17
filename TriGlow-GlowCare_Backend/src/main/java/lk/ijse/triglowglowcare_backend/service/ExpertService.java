package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.ExpertDTO;
import lk.ijse.triglowglowcare_backend.entity.Expert;

import java.util.List;

public interface ExpertService {

    public void saveExpert(ExpertDTO expertDTO);
    public void updateExpert(ExpertDTO expertDTO);
    public void deleteExpert(long expertId);
    public List<ExpertDTO> getAllExperts();

}
