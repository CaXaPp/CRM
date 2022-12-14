package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import esdp.crm.attractor.school.repository.RoleRepository;
import esdp.crm.attractor.school.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final StatusRepository statusRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    public User toUser(RegisterFormDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .surname(dto.getSurname())
                .middleName(dto.getMiddleName())
                .email(dto.getEmail())
                .enabled(true)
                .status(statusRepository.getByName("Active"))
                .password(passwordEncoder.encode(dto.getPassword()))
                .department(departmentRepository.getById(dto.getDepartmentId()))
                .role(roleRepository.getById(dto.getRoleId()))
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .surname(user.getSurname())
                .middleName(user.getMiddleName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
