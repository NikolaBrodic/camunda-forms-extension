package rs.ac.uns.ftn.vehicleinspectionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findOneById(Long id);

    List<Appointment> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Appointment> findByIdInAndDateTimeGreaterThanEqual(List<Long> ids, LocalDateTime startDT);

}