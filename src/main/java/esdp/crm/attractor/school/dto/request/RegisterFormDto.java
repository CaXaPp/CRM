package esdp.crm.attractor.school.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    private Long roleId;
    private boolean enabled = true;
}
