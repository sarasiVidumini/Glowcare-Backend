package lk.ijse.triglowglowcare_backend.service.impl;

import lk.ijse.triglowglowcare_backend.dto.HospitalDTO;
import lk.ijse.triglowglowcare_backend.entity.Hospital;
import lk.ijse.triglowglowcare_backend.repository.HospitalRepo;
import lk.ijse.triglowglowcare_backend.service.HospitalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepo hospitalRepo;
    private final ModelMapper modelMapper;


    @Override
    public void saveHospital(HospitalDTO hospitalDTO) {
        hospitalRepo.save(modelMapper.map(hospitalDTO, Hospital.class));

    }

    @Override
    public void updateHospital(HospitalDTO hospitalDTO) {
        hospitalRepo.save(modelMapper.map(hospitalDTO, Hospital.class));
    }

    @Override
    public void deleteHospital(long hospitalId) {
        hospitalRepo.deleteById(hospitalId);
    }

    @Override
    public List<HospitalDTO> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepo.findAll();
        return modelMapper.map(hospitals,new TypeToken<List<HospitalDTO>>() {}.getType());
    }
}
