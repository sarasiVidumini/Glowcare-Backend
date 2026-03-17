package lk.ijse.triglowglowcare_backend.service.impl;

import lk.ijse.triglowglowcare_backend.dto.ExpertDTO;
import lk.ijse.triglowglowcare_backend.entity.Expert;
import lk.ijse.triglowglowcare_backend.repository.ExpertRepo;
import lk.ijse.triglowglowcare_backend.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepo expertRepo;
    private final ModelMapper modelMapper;


    @Override
    public void saveExpert(ExpertDTO expertDTO) {
        expertRepo.save(modelMapper.map(expertDTO, Expert.class));
    }

    @Override
    public void updateExpert(ExpertDTO expertDTO) {
        expertRepo.save(modelMapper.map(expertDTO, Expert.class));
    }

    @Override
    public void deleteExpert(long expertId) {
        expertRepo.deleteById(expertId);
    }

    @Override
    public List<ExpertDTO> getAllExperts() {
       List<Expert> experts = expertRepo.findAll();
       return modelMapper.map(experts,new TypeToken<List<ExpertDTO>>() {}.getType());
    }
}
