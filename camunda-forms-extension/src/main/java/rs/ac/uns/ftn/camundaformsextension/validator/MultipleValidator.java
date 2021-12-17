package rs.ac.uns.ftn.camundaformsextension.validator;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class MultipleValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        if (submittedValue == null) {
            TypedValue value = validatorContext.getVariableScope().getVariableTyped(validatorContext.getFormFieldHandler().getId());
            return value != null && value.getValue() != null;
        } else if (!(submittedValue instanceof String)) {
            return submittedValue != null;
        } else {
            return submittedValue != null && !((String) submittedValue).isEmpty();
        }
    }

}
