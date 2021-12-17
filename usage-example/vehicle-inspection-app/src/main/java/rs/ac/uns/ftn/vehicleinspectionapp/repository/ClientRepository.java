package rs.ac.uns.ftn.vehicleinspectionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}