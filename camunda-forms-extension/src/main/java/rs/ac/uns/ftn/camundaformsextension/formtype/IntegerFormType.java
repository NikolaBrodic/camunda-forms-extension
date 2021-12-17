package rs.ac.uns.ftn.camundaformsextension.formtype;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.IntegerValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class IntegerFormType extends SimpleFormFieldType {
    public static final String TYPE_NAME = "integer";

    public String getName() {
        return "integer";
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        if (propertyValue instanceof IntegerValue) {
            return propertyValue;
        } else {
            Object value = propertyValue.getValue();
            if (value == null) {
                return Variables.integerValue((Integer) null, propertyValue.isTransient());
            } else if (!(value instanceof Number) && !(value instanceof String)) {
                throw new ProcessEngineException("Value '" + value + "' is not of type Integer.");
            } else {
                return Variables.integerValue(Integer.valueOf(value.toString()), propertyValue.isTransient());
            }
        }
    }

    public Object convertFormValueToModelValue(Object propertyValue) {
        return propertyValue != null && !"".equals(propertyValue) ? Integer.valueOf(propertyValue.toString()) : null;
    }

    public String convertModelValueToFormValue(Object modelValue) {
        return modelValue == null ? null : modelValue.toString();
    }
}
