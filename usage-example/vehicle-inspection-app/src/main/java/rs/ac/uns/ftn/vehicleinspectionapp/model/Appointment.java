package rs.ac.uns.ftn.vehicleinspectionapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    @Column
    private String vehicle;

    @Column(unique = true)
    private LocalDateTime dateTime;

}
