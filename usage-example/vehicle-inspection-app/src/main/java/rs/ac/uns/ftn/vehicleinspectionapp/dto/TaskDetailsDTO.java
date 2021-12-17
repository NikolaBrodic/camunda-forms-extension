package rs.ac.uns.ftn.vehicleinspectionapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.camundaformsextension.dto.ExecutionDTO;
import rs.ac.uns.ftn.vehicleinspectionapp.model.Appointment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailsDTO {

    private ExecutionDTO execution;
    private Appointment appointment;

}
