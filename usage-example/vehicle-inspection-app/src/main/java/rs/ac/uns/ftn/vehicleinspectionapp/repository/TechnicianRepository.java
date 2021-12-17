package rs.ac.uns.ftn.vehicleinspectionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Technician;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    Technician findByUsername(String username);

}
