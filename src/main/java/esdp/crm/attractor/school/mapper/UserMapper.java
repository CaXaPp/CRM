package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import esdp.crm.attractor.school.repository.RoleRepository;
import esdp.crm.attractor.school.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    @Mapping(target = "status", expression = "java(statusRepository.getByName(\"Active\"))")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "department", expression = "java(departmentRepository.getById(dto.getDepartmentId()))")
    @Mapping(target = "role", expression = "java(roleRepository.getById(dto.getRoleId()))")
    public abstract User toUser(RegisterFormDto dto);

    public abstract UserDto toUserDto(User user);
}
