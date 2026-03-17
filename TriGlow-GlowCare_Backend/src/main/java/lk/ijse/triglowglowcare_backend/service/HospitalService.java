package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.HospitalDTO;

import java.util.List;

public interface HospitalService {

    public void saveHospital(HospitalDTO hospitalDTO);
    public void updateHospital(HospitalDTO hospitalDTO);
    public void deleteHospital(long hospitalId);
    public List<HospitalDTO> getAllHospitals();

}
