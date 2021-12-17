package rs.ac.uns.ftn.vehicleinspectionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.camundaformsextension.dto.ExecutionDTO;
import rs.ac.uns.ftn.camundaformsextension.dto.FieldDTO;
import rs.ac.uns.ftn.camundaformsextension.dto.FormDataDTO;
import rs.ac.uns.ftn.camundaformsextension.service.CamundaService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/camunda", produces = MediaType.APPLICATION_JSON_VALUE)
public class CamundaController {

    private static final String PROCESS_VEHICLE_INSPECTION = "Process_VehicleInspection";
    private CamundaService camundaService;

    @GetMapping("/start-process")
    public ResponseEntity<ExecutionDTO> startProcess() {
        ExecutionDTO executionDTO = camundaService.start(PROCESS_VEHICLE_INSPECTION);
        return new ResponseEntity<>(executionDTO, HttpStatus.OK);
    }

    @GetMapping("/form-data")
    public ResponseEntity<FormDataDTO> getFormData(@NotBlank @RequestParam("piId") String procInstanceId,
                                                   @NotBlank @RequestParam String taskId) {
        FormDataDTO formDataDTO = camundaService.getFormData(procInstanceId, taskId);
        return new ResponseEntity<>(formDataDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/submit-form", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submitForm(@NotBlank @RequestParam("piId") String procInstanceId,
                                        @NotBlank @RequestParam String taskId,
                                        @Valid @RequestBody List<FieldDTO> fieldList) {
        camundaService.submitForm(procInstanceId, taskId, fieldList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/complete-task")
    public ResponseEntity<?> completeTask(@NotBlank @RequestParam String taskId) {
        camundaService.completeTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public CamundaController(CamundaService camundaService) {
        this.camundaService = camundaService;
    }
}
