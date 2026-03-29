package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.AppointmentRequestDTO;
import lk.ijse.glowcare_backend.dto.PhysicianDTO;
import java.util.List;

public interface ClinicalService {
    List<PhysicianDTO> getPhysiciansByCity(String city);
    String bookAppointment(AppointmentRequestDTO requestDTO);
    // 🚀 NEW ADMIN METHODS
    PhysicianDTO addPhysician(PhysicianDTO dto);
    PhysicianDTO updatePhysician(Long id, PhysicianDTO dto);
    void deletePhysician(Long id);
}