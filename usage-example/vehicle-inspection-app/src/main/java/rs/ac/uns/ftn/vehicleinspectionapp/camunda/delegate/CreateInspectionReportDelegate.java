package rs.ac.uns.ftn.vehicleinspectionapp.camunda.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.vehicleinspectionapp.enums.FuelType;
import rs.ac.uns.ftn.vehicleinspectionapp.enums.VehicleType;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Appointment;
import rs.ac.uns.ftn.vehicleinspectionapp.model.InspectionReport;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Technician;
import rs.ac.uns.ftn.vehicleinspectionapp.service.AppointmentService;
import rs.ac.uns.ftn.camundaformsextension.service.CamundaService;
import rs.ac.uns.ftn.vehicleinspectionapp.service.InspectionReportService;
import rs.ac.uns.ftn.vehicleinspectionapp.service.TechnicianService;
import rs.ac.uns.ftn.camundaformsextension.util.DateTimeUtil;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateInspectionReportDelegate implements JavaDelegate {

    private InspectionReportService inspectionReportService;
    private AppointmentService appointmentService;
    private TechnicianService technicianService;
    private CamundaService camundaService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long appointmentId = (Long) execution.getVariable("appointmentId");
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment == null) {
            throw new BpmnError("noAppointmentOrTechnician");
        }

        Technician technician = technicianService.findByUsername("petar");
        if (technician == null) {
            throw new BpmnError("noAppointmentOrTechnician");
        }

        String[] selectedOptions = camundaService.getSelectedOptions((String) execution.getVariable("fuelTypes_selected"));
        List<FuelType> selectedFuelTypes = Arrays.stream(selectedOptions)
                .map(FuelType::valueOf).collect(Collectors.toList());

        InspectionReport report = InspectionReport.builder()
                .vehicleType(VehicleType.valueOf((String) execution.getVariable("vehicleType")))
                .vehicle((String) execution.getVariable("vehicle"))
                .productionYear((Integer) execution.getVariable("productionYear"))
                .engineCapacity((Integer) execution.getVariable("engineCapacity"))
                .enginePower((Double) execution.getVariable("enginePower"))
                .fuelTypes(selectedFuelTypes)
                .hasNoMajorMechanicalDefects((Boolean) execution.getVariable("hasNoMajorMechanicalDefects"))
                .hasNoMajorBodyDefects((Boolean) execution.getVariable("hasNoMajorBodyDefects"))
                .additionalNotes((String) execution.getVariable("additionalNotes"))
                .hasPassed(Boolean.parseBoolean((String) execution.getVariable("hasPassed")))
                .inspectionDate(DateTimeUtil.convertToLocalDate((Date) execution.getVariable("inspectionDate")))
                .inspectionTime(LocalTime.parse((String) execution.getVariable("inspectionTime")))
                .appointment(appointment)
                .technician(technician)
                .build();

        InspectionReport savedReport = inspectionReportService.save(report);
        execution.setVariable("reportId", savedReport.getId());
    }

    @Autowired
    public CreateInspectionReportDelegate(InspectionReportService inspectionReportService, AppointmentService appointmentService,
                                          TechnicianService technicianService, CamundaService camundaService) {
        this.inspectionReportService = inspectionReportService;
        this.appointmentService = appointmentService;
        this.technicianService = technicianService;
        this.camundaService = camundaService;
    }
}
