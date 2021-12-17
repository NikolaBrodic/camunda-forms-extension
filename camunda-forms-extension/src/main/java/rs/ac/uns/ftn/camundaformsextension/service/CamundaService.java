package rs.ac.uns.ftn.camundaformsextension.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.camundaformsextension.dto.ExecutionDTO;
import rs.ac.uns.ftn.camundaformsextension.dto.FieldDTO;
import rs.ac.uns.ftn.camundaformsextension.dto.FormDataDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CamundaService {

    // |&| je uveden kao oznaka za spajanje niza vrednosti istog tipa
    // Koristi se kombinovanje i split-ovanje selektovanih opcija iz multiple <select> elementa
    private static final String splitStr = "|&|";
    private static final String splitRegex = "\\|&\\|";

    private RuntimeService runtimeService;
    private TaskService taskService;
    private FormService formService;

    public ExecutionDTO start(String processId) {
        ProcessInstance procInstance = runtimeService.startProcessInstanceByKey(processId);
        String taskId = this.getTaskId(procInstance.getId());

        return new ExecutionDTO(procInstance.getId(), taskId);
    }

    public FormDataDTO getFormData(String procInstanceId, String taskId) {
        TaskFormData tfd = formService.getTaskFormData(taskId);
        List<FormField> formFields = tfd.getFormFields();
        this.setEnumValues(procInstanceId, formFields);

        return new FormDataDTO(procInstanceId, taskId, formFields);
    }

    private void setEnumValues(String procInstanceId, List<FormField> formFields) {
        for (FormField formField : formFields) {
            if (formField.getTypeName().equals("enum")) {
                EnumFormType enumType = (EnumFormType) formField.getType();
                Map<String, String> enumValues = enumType.getValues();
                Map<String, String> options = this.extractOptions(procInstanceId, formField);
                if (options == null || options.isEmpty()) {
                    continue;
                }
                if (enumValues.size() != options.size()) {
                    enumValues.clear();
                    enumValues.putAll(options);
                }
            }
        }
    }

    private Map<String, String> extractOptions(String procInstanceId, FormField formField) {
        String optionsProcVar = formField.getProperties().get("options");
        if (optionsProcVar == null || optionsProcVar.isBlank()) {
            return null;
        }
        Object optionsObj = runtimeService.getVariable(procInstanceId, optionsProcVar);
        if (optionsObj == null) {
            return null;
        }

        return (Map<String, String>) optionsObj;
    }

    public void submitForm(String procInstanceId, String taskId, List<FieldDTO> fieldList) {
        Map<String, Object> fieldMap = fieldList.stream()
                .collect(Collectors.toMap(FieldDTO::getId, field -> this.getFieldValue(procInstanceId, field)));

        formService.submitTaskForm(taskId, fieldMap);
    }

    private String getFieldValue(String procInstanceId, FieldDTO field) {
        String fieldValue = field.getValue();
        if (fieldValue.contains(splitStr)) {
            this.setProcessVariable(procInstanceId, field.getId() + "_selected", fieldValue);
            fieldValue = this.getSelectedOptions(fieldValue)[0];
        }

        return fieldValue;
    }

    public String[] getSelectedOptions(String options) {
        return options.split(splitRegex);
    }

    public Object getProcessVariable(String procInstanceId, String name) {
        return runtimeService.getVariable(procInstanceId, name);
    }

    public void setProcessVariable(String procInstanceId, String variable, Object value) {
        runtimeService.setVariable(procInstanceId, variable, value);
    }

    public String getTaskId(String procInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(procInstanceId).list().get(0);
        return task.getId();
    }

    public List<TaskDto> getTasksByTdKey(String tdKey) {
        List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(tdKey).list();
        return tasks.stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByAssignee(String username) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
        return tasks.stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    @Autowired
    public CamundaService(RuntimeService runtimeService, TaskService taskService, FormService formService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.formService = formService;
    }
}
