package rs.ac.uns.ftn.vehicleinspectionapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Technician;
import rs.ac.uns.ftn.vehicleinspectionapp.service.TechnicianService;

@Component
public class DummyDataLoader {

    private TechnicianService technicianService;

    @EventListener(ApplicationReadyEvent.class)
    public void insertData() {
        if (technicianService.findAll().isEmpty()) {
            Technician technician = Technician.builder()
                    .firstName("Petar")
                    .lastName("Petrović")
                    .username("petar")
                    .password("petar123")
                    .build();
            technicianService.save(technician);
        }
    }

    @Autowired
    public DummyDataLoader(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }
}
