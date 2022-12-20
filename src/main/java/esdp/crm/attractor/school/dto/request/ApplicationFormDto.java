package esdp.crm.attractor.school.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationFormDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("company")
    @NotBlank
    private String company;

    @JsonProperty("product_id")
    @NotNull
    private Long productId;

    @JsonProperty("phone")
    @NotNull
    private String phone;

    @JsonProperty("email")
    @NotBlank
    private String email;

    @JsonProperty("address")
    @NotBlank
    private String address;

    @JsonProperty("source_id")
    @NotNull
    private Long sourceId;

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("status_id")
    private Long statusId;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
