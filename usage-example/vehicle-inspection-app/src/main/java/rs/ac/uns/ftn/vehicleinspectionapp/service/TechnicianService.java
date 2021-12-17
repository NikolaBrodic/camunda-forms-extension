package rs.ac.uns.ftn.vehicleinspectionapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Technician;
import rs.ac.uns.ftn.vehicleinspectionapp.repository.TechnicianRepository;

import java.util.List;

@Service
public class TechnicianService {

    private TechnicianRepository technicianRepository;

    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    public Technician findByUsername(String username) {
        return technicianRepository.findByUsername(username);
    }

    public Technician save(Technician technician) {
        return technicianRepository.save(technician);
    }

    @Autowired
    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }
}
