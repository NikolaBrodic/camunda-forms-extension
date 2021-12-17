package rs.ac.uns.ftn.vehicleinspectionapp.service;

import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.camundaformsextension.dto.ExecutionDTO;
import rs.ac.uns.ftn.vehicleinspectionapp.dto.TaskDetailsDTO;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Appointment;
import rs.ac.uns.ftn.vehicleinspectionapp.repository.AppointmentRepository;
import rs.ac.uns.ftn.camundaformsextension.service.CamundaService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private CamundaService camundaService;

    public Appointment findById(Long id) {
        return appointmentRepository.findOneById(id);
    }

    public List<LocalDateTime> findAvailableWithinWeek() {
        // Postavi pocetni datum i vreme na prvi sledeci dan u 09:00
        LocalDateTime startDT = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1).withHour(9);
        LocalDateTime endDT = startDT.plusDays(7);

        List<LocalDateTime> available = new ArrayList<>();
        // Iz baze vrati sve zakazane appointment-e
        List<Appointment> scheduledAppointments = appointmentRepository.findByDateTimeBetween(startDT, endDT);
        // Izvuci iz njih zauzete dateTime
        List<LocalDateTime> scheduled = scheduledAppointments.stream()
                .map(Appointment::getDateTime)
                .collect(Collectors.toList());

        // Popuni listu svim dateTime za narednih 7 dana pocev od sutra, bez vec zauzetih termina
        for (LocalDateTime currentDT = startDT; currentDT.isBefore(endDT); currentDT = incrementDateTime(currentDT)) {
            if (!scheduled.contains(currentDT)) {
                available.add(currentDT);
            }
        }

        return available;
    }

    public List<TaskDetailsDTO> findUndoneFromToday() {
        // Vrati sve InspectionReportForm taskove
        List<TaskDto> techniciansTasks = camundaService.getTasksByTdKey("Activity_InspectionReportForm");

        // Izvuci iz svakog od njih appointmentId
        List<Long> appointmentIds = new ArrayList<>();
        for (TaskDto task : techniciansTasks) {
            appointmentIds.add((Long) camundaService.getProcessVariable(task.getProcessInstanceId(), "appointmentId"));
        }

        // Postavi startDT na pocetak danasnjeg dana
        LocalDateTime startDT = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).withHour(9);

        // Iz baze vrati sve nezavrsene appointment-e pocev od danas
        Map<Long, Appointment> appointmentMap = appointmentRepository
                .findByIdInAndDateTimeGreaterThanEqual(appointmentIds, startDT)
                .stream().collect(Collectors.toMap(Appointment::getId, Function.identity()));

        // Kreiraj DTO listu onih taskova ciji appointmentId je vracen iz baze
        List<TaskDetailsDTO> taskDetailsList = new ArrayList<>();
        for (TaskDto task : techniciansTasks) {
            Long appointmentId = (Long) camundaService.getProcessVariable(task.getProcessInstanceId(), "appointmentId");
            if (appointmentMap.containsKey(appointmentId)) {
                TaskDetailsDTO taskDetails = new TaskDetailsDTO();
                taskDetails.setExecution(new ExecutionDTO(task.getProcessInstanceId(), task.getId()));
                taskDetails.setAppointment(appointmentMap.get(appointmentId));
                taskDetailsList.add(taskDetails);
            }
        }

        return taskDetailsList;
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    private LocalDateTime incrementDateTime(LocalDateTime dateTime) {
        dateTime = dateTime.plusHours(1);
        if (dateTime.getHour() == 17) {
            dateTime = dateTime.truncatedTo(ChronoUnit.DAYS).plusDays(1).withHour(9);
        }
        return dateTime;
    }

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, CamundaService camundaService) {
        this.appointmentRepository = appointmentRepository;
        this.camundaService = camundaService;
    }
}
