package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import esdp.crm.attractor.school.repository.RoleRepository;
import esdp.crm.attractor.school.repository.StatusRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserMapper {
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserRepository userRepository;

    @Mapping(target = "status", expression = "java(statusRepository.getByName(\"Active\"))")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "department", expression = "java(departmentRepository.getById(dto.getDepartmentId()))")
    @Mapping(target = "role", expression = "java(roleRepository.getById(dto.getRoleId()))")
    public abstract User toUser(RegisterFormDto dto);

    public Optional<User> toUser(UserDto dto) {
        return userRepository.findById(dto.getId());
    }

    @Mapping(target = "status", expression = "java(statusRepository.getByName(dto.getStatus()))")
    @Mapping(target = "department", expression = "java(departmentRepository.getByName(dto.getDepartment()))")
    @Mapping(target = "role", expression = "java(roleRepository.getByName(dto.getRole()))")
    @Mapping(target = "password", expression = "java(userRepository.getPasswordById(dto.getId()))")
    public abstract User toUserFromDto(UserDto dto);

    @Mapping(target = "role", expression = "java(user.getRole().getName())")
    @Mapping(target = "status", expression = "java(user.getStatus().getName())")
    @Mapping(target = "department", expression = "java(user.getDepartment().getName())")
    public abstract UserDto toUserDto(User user);
}
