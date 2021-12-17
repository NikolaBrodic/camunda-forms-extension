package rs.ac.uns.ftn.vehicleinspectionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.vehicleinspectionapp.dto.TaskDetailsDTO;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Appointment;
import rs.ac.uns.ftn.vehicleinspectionapp.service.AppointmentService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@NotNull @Positive @PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskDetailsDTO>> getUndoneFromToday() {
        return new ResponseEntity<>(appointmentService.findUndoneFromToday(), HttpStatus.OK);
    }

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

}
