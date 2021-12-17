package rs.ac.uns.ftn.camundaformsextension.formtype;

import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class RadioFormType extends SimpleFormFieldType {
    public static final String TYPE_NAME = "radio";

    public String getName() {
        return "radio";
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        if (propertyValue instanceof StringValue) {
            return propertyValue;
        } else {
            Object value = propertyValue.getValue();
            if (value == null) {
                return Variables.stringValue(null, propertyValue.isTransient());
            }
            return Variables.stringValue(value.toString(), propertyValue.isTransient());
        }
    }

    public Object convertFormValueToModelValue(Object propertyValue) {
        return propertyValue.toString();
    }

    public String convertModelValueToFormValue(Object modelValue) {
        return (String) modelValue;
    }
}
