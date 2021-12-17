package rs.ac.uns.ftn.vehicleinspectionapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import rs.ac.uns.ftn.vehicleinspectionapp.enums.FuelType;
import rs.ac.uns.ftn.vehicleinspectionapp.enums.VehicleType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class InspectionReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column
    private String vehicle;

    @Column
    private int productionYear;

    @Column
    private int engineCapacity;

    @Column
    private double enginePower;

    @ElementCollection(targetClass = FuelType.class)
    @CollectionTable(name = "report_fuel_types")
    @Enumerated(EnumType.STRING)
    private List<FuelType> fuelTypes;

    @Column
    private boolean hasNoMajorMechanicalDefects;

    @Column
    private boolean hasNoMajorBodyDefects;

    @ElementCollection
    @CollectionTable(name = "report_images")
    @Column(name = "image_path")
    private List<String> imagesPaths = new ArrayList<>();

    @Column(length = 1000)
    private String additionalNotes;

    @Column
    private boolean hasPassed;

    @Column
    private LocalDate inspectionDate;

    @Column
    private LocalTime inspectionTime;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Appointment appointment;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Technician technician;

}
