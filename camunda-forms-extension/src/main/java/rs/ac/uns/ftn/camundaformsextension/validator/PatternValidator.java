package rs.ac.uns.ftn.camundaformsextension.validator;

import org.camunda.bpm.engine.impl.form.validator.AbstractTextValueValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldConfigurationException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternValidator extends AbstractTextValueValidator {

    protected boolean validate(String submittedValue, String regex) {
        try {
            Pattern.compile(regex);
            return submittedValue.matches(regex);
        } catch (PatternSyntaxException exception) {
            throw new FormFieldConfigurationException(regex, "Cannot validate \"pattern\": configuration " + regex + " is not a valid regular expression");
        }
    }

}
