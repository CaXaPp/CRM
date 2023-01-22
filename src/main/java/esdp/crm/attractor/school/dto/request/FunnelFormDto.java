package esdp.crm.attractor.school.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunnelFormDto {
    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("statuses")
    @NotEmpty
    private List<String> statuses;

    @JsonProperty("departments")
    @NotEmpty
    private List<Long> departments;
}
