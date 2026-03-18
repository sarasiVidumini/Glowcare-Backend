package lk.ijse.triglowglowcare_backend.service.impl;

import lk.ijse.triglowglowcare_backend.dto.AppointmentDTO;
import lk.ijse.triglowglowcare_backend.entity.Appointment;
import lk.ijse.triglowglowcare_backend.repository.AppointmentRepo;
import lk.ijse.triglowglowcare_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;
    @Override
    public void saveAppointment(AppointmentDTO appointmentDTO) {
        appointmentRepo.save(modelMapper.map(appointmentDTO, Appointment.class));
    }

    @Override
    public void updateAppointment(AppointmentDTO appointmentDTO) {
        appointmentRepo.save(modelMapper.map(appointmentDTO, Appointment.class));
    }

    @Override
    public void deleteAppointment(long appointmentId) {
        appointmentRepo.deleteById(appointmentId);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepo.findAll();
        return modelMapper.map(appointments,new TypeToken<List<AppointmentDTO>>() {}.getType());
    }
}
