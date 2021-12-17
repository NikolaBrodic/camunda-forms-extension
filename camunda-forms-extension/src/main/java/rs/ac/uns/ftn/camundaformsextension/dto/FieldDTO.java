package rs.ac.uns.ftn.camundaformsextension.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FieldDTO {

    @NotBlank(message = "Field identifier is blank.")
    private String id;

    @NotNull(message = "Field value is null.")
    private String value;

    public FieldDTO() {
    }

    public FieldDTO(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
