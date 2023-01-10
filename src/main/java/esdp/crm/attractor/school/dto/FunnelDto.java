package esdp.crm.attractor.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunnelDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
