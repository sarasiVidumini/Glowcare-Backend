package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {

    public void saveAppointment(AppointmentDTO appointmentDTO);
    public void updateAppointment(AppointmentDTO appointmentDTO);
    public void deleteAppointment(long appointmentId);
    public List<AppointmentDTO> getAllAppointments();

}
