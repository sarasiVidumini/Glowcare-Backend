package lk.ijse.glowcare_backend.service.impl;
import lk.ijse.glowcare_backend.dto.*;
import lk.ijse.glowcare_backend.entity.*;
import lk.ijse.glowcare_backend.repository.*;
import lk.ijse.glowcare_backend.service.ClinicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClinicalServiceImpl implements ClinicalService {

    private final PhysicianRepository physicianRepo;
    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;

    @Override
    public List<PhysicianDTO> getPhysiciansByCity(String city) {
        List<Physician> docs = (city != null && !city.trim().isEmpty())
                ? physicianRepo.findByCityContainingIgnoreCase(city)
                : physicianRepo.findAll();

        return docs.stream().map(p -> PhysicianDTO.builder()
                .id(p.getId()).name(p.getName()).specialization(p.getSpecialization())
                .hospitalName(p.getHospitalName()).city(p.getCity()).availableDays(p.getAvailableDays())
                .build()).collect(Collectors.toList());
    }

    // Inside ClinicalServiceImpl.java
    // Inside ClinicalServiceImpl.java -> bookAppointment method

    @Override
    public String bookAppointment(AppointmentRequestDTO request) {
        // 🚀 Fetch using the User's Email!
        User clientUser = userRepo.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found in database with email: " + request.getUserEmail()));

        Physician doctor = physicianRepo.findById(request.getPhysicianId())
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        // Date Validation
        java.time.LocalDate requestedDate = java.time.LocalDate.parse(request.getDate());
        String dayOfWeek = requestedDate.getDayOfWeek().getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.ENGLISH);

        if (!doctor.getAvailableDays().contains(dayOfWeek)) {
            throw new RuntimeException("Dr. " + doctor.getName() + " is not available on " + dayOfWeek + "s.");
        }

        // Save Appointment
        Appointment appointment = Appointment.builder()
                .user(clientUser)
                .physician(doctor)
                .appointmentDate(requestedDate)
                .reason(request.getReason())
                .status("CONFIRMED")
                .build();

        appointmentRepo.save(appointment);
        return "Success! Appointment booked for " + requestedDate;
    }

    @Override
    public PhysicianDTO addPhysician(PhysicianDTO dto) {
        Physician physician = Physician.builder()
                .name(dto.getName())
                .specialization(dto.getSpecialization())
                .hospitalName(dto.getHospitalName())
                .city(dto.getCity())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .availableDays(dto.getAvailableDays())
                .build();

        Physician saved = physicianRepo.save(physician);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public PhysicianDTO updatePhysician(Long id, PhysicianDTO dto) {
        Physician existing = physicianRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));

        existing.setName(dto.getName());
        existing.setSpecialization(dto.getSpecialization());
        existing.setHospitalName(dto.getHospitalName());
        existing.setCity(dto.getCity());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setAvailableDays(dto.getAvailableDays());

        physicianRepo.save(existing);
        dto.setId(existing.getId());
        return dto;
    }

    @Override
    public void deletePhysician(Long id) {
        Physician existing = physicianRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));
        physicianRepo.delete(existing);
    }
}