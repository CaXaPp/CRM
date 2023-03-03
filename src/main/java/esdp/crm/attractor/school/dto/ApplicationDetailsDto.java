package esdp.crm.attractor.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDetailsDto {
    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("successCount")
    private int successCount;

    @JsonProperty("failCount")
    private int failCount;

    @JsonProperty("totalSum")
    private double totalSum;

    @JsonProperty("successSum")
    private double successSum;

    @JsonProperty("failSum")
    private double failSum;

    @JsonProperty("planSum")
    private List<PlanSumDto> planSum;

    @JsonProperty("sumLastMonth")
    private Double lastMonthSum;
}
