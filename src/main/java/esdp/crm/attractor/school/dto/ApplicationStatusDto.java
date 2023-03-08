package esdp.crm.attractor.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import esdp.crm.attractor.school.entity.Funnel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("funnel")
    private Funnel funnel;
}
