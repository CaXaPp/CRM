package esdp.crm.attractor.school.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import esdp.crm.attractor.school.entity.Role;
import lombok.Data;

@Data
public class RegisterFormDto {
    @Size(max = 25)
    @NotBlank
    private String firstName;

    @Size(max = 200)
    @NotBlank
    private String surname;

    @NotBlank
    private String middleName;

    @Email
    @Size(max = 25)
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Long departmentId;

    private Role role;
}
