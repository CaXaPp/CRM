package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String surname;
    private String middleName;
    private String email;
    private Role role;
}
