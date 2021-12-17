package rs.ac.uns.ftn.vehicleinspectionapp.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Appointment;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Client;
import rs.ac.uns.ftn.vehicleinspectionapp.service.AppointmentService;

import java.time.LocalDateTime;

@Service
public class CreateAppointmentDelegate implements JavaDelegate {

    private AppointmentService appointmentService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Client client = Client.builder()
                .firstName((String) execution.getVariable("firstName"))
                .lastName((String) execution.getVariable("lastName"))
                .phoneNum((String) execution.getVariable("phoneNum"))
                .build();

        Appointment appointment = Appointment.builder()
                .client(client)
                .vehicle((String) execution.getVariable("vehicle"))
                .dateTime(LocalDateTime.parse((String) execution.getVariable("dateTime")))
                .build();

        Appointment savedApt = appointmentService.save(appointment);
        execution.setVariable("appointmentId", savedApt.getId());
    }

    @Autowired
    public CreateAppointmentDelegate(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

}
