package esdp.crm.attractor.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanSumRequest {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal sum;
}
