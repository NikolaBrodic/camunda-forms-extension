package rs.ac.uns.ftn.vehicleinspectionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.vehicleinspectionapp.model.InspectionReport;

@Repository
public interface InspectionReportRepository extends JpaRepository<InspectionReport, Long> {

    InspectionReport findOneById(Long id);

}