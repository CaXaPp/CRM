package esdp.crm.attractor.school.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFormDto {
    @JsonProperty("operation_id")
    @NotNull
    private Long operationId;

    @JsonProperty("deadline")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime deadline;

    @JsonProperty("employee_id")
    @NotNull
    private Long employeeId;

    @JsonProperty("type_id")
    @NotNull
    private Long typeId;

    @JsonProperty("description")
    private String description;
}
