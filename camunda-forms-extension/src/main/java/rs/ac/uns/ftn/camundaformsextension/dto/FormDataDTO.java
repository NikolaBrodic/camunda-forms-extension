package rs.ac.uns.ftn.camundaformsextension.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class FormDataDTO {

    private String procInstanceId;
    private String taskId;
    private List<FormField> formFields;

    public FormDataDTO() {
    }

    public FormDataDTO(String procInstanceId, String taskId, List<FormField> formFields) {
        this.procInstanceId = procInstanceId;
        this.taskId = taskId;
        this.formFields = formFields;
    }

    public String getProcInstanceId() {
        return procInstanceId;
    }

    public void setProcInstanceId(String procInstanceId) {
        this.procInstanceId = procInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }
}

