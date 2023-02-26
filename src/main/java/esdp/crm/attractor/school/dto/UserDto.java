package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.entity.Status;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String surname;
    private String middleName;
    private String email;
    private String role;
    private boolean enabled;
    private DepartmentDto department;
    private String status;

    public String getFIO(){
        return String.format("%s %s %s", surname, firstName, middleName);
    }
}
