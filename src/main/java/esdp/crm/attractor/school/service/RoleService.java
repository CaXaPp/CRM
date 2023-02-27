package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.RoleDto;
import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.mapper.RoleMapper;
import esdp.crm.attractor.school.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    public List<RoleDto> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Role getByName(String name) { return roleRepository.getRoleByName(name); }

}
