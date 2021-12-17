package rs.ac.uns.ftn.camundaformsextension.validator;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import rs.ac.uns.ftn.camundaformsextension.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractValueValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValueObj, FormFieldValidatorContext validatorContext) {
        if (submittedValueObj == null) {
            return true;
        } else if (submittedValueObj instanceof String) {
            String submittedString = (String) submittedValueObj;
            String configurationString = validatorContext.getConfiguration();

            LocalDate submittedDate = DateTimeUtil.getLocalDate(submittedString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate configDate = DateTimeUtil.getLocalDate(configurationString, DateTimeFormatter.ISO_LOCAL_DATE);
            if (submittedDate != null && configDate != null) {
                return this.validate(submittedDate, configDate);
            }

            LocalTime submittedTime = DateTimeUtil.getLocalTime(submittedString, DateTimeFormatter.ISO_LOCAL_TIME);
            LocalTime configTime = DateTimeUtil.getLocalTime(configurationString, DateTimeFormatter.ISO_LOCAL_TIME);
            if (submittedTime != null && configTime != null) {
                return this.validate(submittedTime, configTime);
            }

            try {
                double submittedNum = Double.parseDouble(submittedString);
                double configNum = Double.parseDouble(configurationString);
                return this.validate(submittedNum, configNum);
            } catch (NumberFormatException var6) {
                return false;
            }
        } else {
            throw new FormFieldValidationException("Value validator " + this.getClass().getSimpleName() + " cannot be used on non-string value " + submittedValueObj);
        }

    }

    public abstract boolean validate(Double submittedNum, Double configNum);

    public abstract boolean validate(LocalDate submittedDate, LocalDate configDate);

    public abstract boolean validate(LocalTime submittedTime, LocalTime configTime);

}
