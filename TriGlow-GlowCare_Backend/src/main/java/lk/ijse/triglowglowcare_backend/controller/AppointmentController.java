package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.AppointmentDTO;
import lk.ijse.triglowglowcare_backend.service.AppointmentService;
import lk.ijse.triglowglowcare_backend.service.impl.AppointmentServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated

public class AppointmentController {

    private final AppointmentServiceImpl appointmentServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        appointmentServiceImpl.saveAppointment(appointmentDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Appointment saved Successfully",null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        appointmentServiceImpl.updateAppointment(appointmentDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Appointment updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<APIResponse<String>> deleteAppointment(@PathVariable long appointmentId) {
        appointmentServiceImpl.deleteAppointment(appointmentId);
        return new ResponseEntity<>(new APIResponse<>(200,"Appointment deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<AppointmentDTO>>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentServiceImpl.getAllAppointments();
        return new ResponseEntity<>(new APIResponse<>(200,"Appointment List Successfully",appointments), HttpStatus.OK);
    }
}
