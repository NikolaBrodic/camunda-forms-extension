package rs.ac.uns.ftn.camundaformsextension.dto;

public class ExecutionDTO {

    private String procInstanceId;
    private String taskId;

    public ExecutionDTO() {
    }

    public ExecutionDTO(String procInstanceId, String taskId) {
        this.procInstanceId = procInstanceId;
        this.taskId = taskId;
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
}