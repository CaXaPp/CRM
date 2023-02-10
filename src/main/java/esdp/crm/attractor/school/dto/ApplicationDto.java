package esdp.crm.attractor.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.ClientSource;
import esdp.crm.attractor.school.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("company")
    private String company;

    @JsonProperty("product")
    private ProductDto product;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("employee")
    private UserDto employee;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("source")
    private ClientSource source;

    @JsonProperty("status")
    private ApplicationStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ApplicationDto that = (ApplicationDto) obj;
        return this.getId() != null && this.getId().equals(that.getId());
    }
}
