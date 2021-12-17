package rs.ac.uns.ftn.vehicleinspectionapp.camunda.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.vehicleinspectionapp.enums.FuelType;
import rs.ac.uns.ftn.vehicleinspectionapp.service.AppointmentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SelectOptionsListener implements ExecutionListener {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. 'at' HH:mm");

    private AppointmentService appointmentService;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        List<LocalDateTime> availableAppointments = appointmentService.findAvailableWithinWeek();
        LinkedHashMap<String, String> availableApptMap = availableAppointments.stream()
                .collect(Collectors.toMap(LocalDateTime::toString, dateTime -> dateTime.format(formatter),
                        (e1, e2) -> e2, LinkedHashMap::new));
        execution.setVariable("availableApptOptions", availableApptMap);

        LinkedHashMap<String, String> fuelTypesMap = Arrays.stream(FuelType.values())
                .collect(Collectors.toMap(Enum::toString, FuelType::getDisplayName,
                        (e1, e2) -> e2, LinkedHashMap::new));
        execution.setVariable("fuelTypeOptions", fuelTypesMap);
    }

    @Autowired
    public SelectOptionsListener(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

}
