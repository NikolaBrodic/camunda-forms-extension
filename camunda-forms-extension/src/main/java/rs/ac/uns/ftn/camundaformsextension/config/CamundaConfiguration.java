package rs.ac.uns.ftn.camundaformsextension.config;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.camundaformsextension.formtype.*;
import rs.ac.uns.ftn.camundaformsextension.validator.*;

@Component
public class CamundaConfiguration extends AbstractCamundaConfiguration {

    @Override
    public void preInit(SpringProcessEngineConfiguration config) {
        // Register custom types
        config.getCustomFormTypes().add(new DoubleFormType());
        config.getCustomFormTypes().add(new FileFormType());
        config.getCustomFormTypes().add(new IntegerFormType());
        config.getCustomFormTypes().add(new RadioFormType());
        config.getCustomFormTypes().add(new TimeFormType());
        config.getCustomFormTypes().add(new TextAreaFormType());

        // Register custom validators
        config.getCustomFormFieldValidators().put("accept", AcceptValidator.class);
        config.getCustomFormFieldValidators().put("max", MaxValueValidator.class);
        config.getCustomFormFieldValidators().put("min", MinValueValidator.class);
        config.getCustomFormFieldValidators().put("multiple", MultipleValidator.class);
        config.getCustomFormFieldValidators().put("pattern", PatternValidator.class);
        config.getCustomFormFieldValidators().put("step", StepValidator.class);
    }
}
