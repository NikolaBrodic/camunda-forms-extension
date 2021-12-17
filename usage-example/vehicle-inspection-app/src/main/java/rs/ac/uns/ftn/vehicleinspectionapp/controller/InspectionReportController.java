package rs.ac.uns.ftn.vehicleinspectionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.vehicleinspectionapp.model.InspectionReport;
import rs.ac.uns.ftn.vehicleinspectionapp.service.InspectionReportService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/inspection-reports")
public class InspectionReportController {

    private InspectionReportService inspectionReportService;

    @GetMapping
    public ResponseEntity<List<InspectionReport>> getAll() {
        return new ResponseEntity<>(inspectionReportService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@NotBlank @RequestParam("piId") String procInstanceId,
                                          @NotNull @RequestParam MultipartFile[] images) {
        inspectionReportService.saveImagesAsFiles(images, procInstanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public InspectionReportController(InspectionReportService inspectionReportService) {
        this.inspectionReportService = inspectionReportService;
    }
}
