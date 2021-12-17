package rs.ac.uns.ftn.camundaformsextension.formtype;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.DoubleValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class DoubleFormType extends SimpleFormFieldType {
    public static final String TYPE_NAME = "double";

    public String getName() {
        return "double";
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        if (propertyValue instanceof DoubleValue) {
            return propertyValue;
        } else {
            Object value = propertyValue.getValue();
            if (value == null) {
                return Variables.doubleValue((Double) null, propertyValue.isTransient());
            } else if (!(value instanceof Number) && !(value instanceof String)) {
                throw new ProcessEngineException("Value '" + value + "' is not of type Double.");
            } else {
                return Variables.doubleValue(Double.valueOf(value.toString()), propertyValue.isTransient());
            }
        }
    }

    public Object convertFormValueToModelValue(Object propertyValue) {
        return propertyValue != null && !"".equals(propertyValue) ? Double.valueOf(propertyValue.toString()) : null;
    }

    public String convertModelValueToFormValue(Object modelValue) {
        return modelValue == null ? null : modelValue.toString();
    }
}