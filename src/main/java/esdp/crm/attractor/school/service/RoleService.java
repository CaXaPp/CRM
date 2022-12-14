package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
