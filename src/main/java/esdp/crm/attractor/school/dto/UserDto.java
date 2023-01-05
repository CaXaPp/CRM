package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.Role;
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
    private Role role;

    public String getFIO(){
        return String.format("%s %s %s", surname, firstName, middleName);
    }
}
