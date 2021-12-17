package rs.ac.uns.ftn.camundaformsextension.formtype;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import rs.ac.uns.ftn.camundaformsextension.util.DateTimeUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormType extends SimpleFormFieldType {
    public static final String TYPE_NAME = "time";

    public String getName() {
        return "time";
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        if (propertyValue instanceof StringValue) {
            return propertyValue;
        } else {
            Object value = propertyValue.getValue();
            if (value == null) {
                return Variables.stringValue(null, propertyValue.isTransient());
            } else if (value instanceof String) {
                LocalTime timeValue = DateTimeUtil.getLocalTime(value.toString(), DateTimeFormatter.ISO_LOCAL_TIME);
                if (timeValue == null) {
                    throw new ProcessEngineException("Value '" + value + "' is not of type LocalTime.");
                } else {
                    return Variables.stringValue(value.toString(), propertyValue.isTransient());
                }
            } else {
                throw new ProcessEngineException("Value '" + value + "' is not of type LocalTime.");
            }
        }
    }

    public Object convertFormValueToModelValue(Object propertyValue) {
        return propertyValue != null && !"".equals(propertyValue) ?
                DateTimeUtil.getLocalTime(propertyValue.toString(), DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    public String convertModelValueToFormValue(Object modelValue) {
        return modelValue == null ? null : modelValue.toString();
    }
}
