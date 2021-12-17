package rs.ac.uns.ftn.vehicleinspectionapp.camunda.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("rs.ac.uns.ftn.camundaformsextension")
public class CamundaPluginConfig {
    // Skeniranje CamundaFormsExtension Plugin-a da bi se omogucio
    // dependency injection upotrebom @Autowired anotacije
}
