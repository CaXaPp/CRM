package esdp.crm.attractor.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDetailsAndStatusDto {
    @JsonProperty("status")
    private String status;

    @JsonProperty("count")
    private int count;

    @JsonProperty("sum")
    private double sum;
}
