package rs.ac.uns.ftn.vehicleinspectionapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.vehicleinspectionapp.model.InspectionReport;
import rs.ac.uns.ftn.vehicleinspectionapp.repository.InspectionReportRepository;
import rs.ac.uns.ftn.camundaformsextension.service.CamundaService;
import rs.ac.uns.ftn.vehicleinspectionapp.util.ImageUtil;

import java.util.List;

@Service
public class InspectionReportService {

    private InspectionReportRepository inspectionReportRepository;
    private CamundaService camundaService;

    public InspectionReport findById(Long id) {
        return inspectionReportRepository.findOneById(id);
    }

    public List<InspectionReport> findAll() {
        return inspectionReportRepository.findAll(Sort.by("inspectionDate").descending()
                .and(Sort.by("inspectionTime").descending()));
    }

    public InspectionReport save(InspectionReport inspectionReport) {
        return inspectionReportRepository.save(inspectionReport);
    }

    public void saveImagesAsFiles(MultipartFile[] images, String procInstanceId) {
        Long reportId = (Long) camundaService.getProcessVariable(procInstanceId, "reportId");
        InspectionReport report = findById(reportId);
        if (report == null) {
            throw new RuntimeException("Report with id=" + reportId + " doesn't exist.");
        }

        for (MultipartFile image : images) {
            String path = ImageUtil.save(image);
            report.getImagesPaths().add(path);
        }

        String taskId = camundaService.getTaskId(procInstanceId);
        camundaService.completeTask(taskId);
    }

    @Autowired
    public InspectionReportService(InspectionReportRepository inspectionReportRepository, CamundaService camundaService) {
        this.inspectionReportRepository = inspectionReportRepository;
        this.camundaService = camundaService;
    }

}
