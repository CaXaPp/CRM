package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.EmailExistsException;
import esdp.crm.attractor.school.mapper.UserMapper;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(RegisterFormDto dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailExistsException("User with email " + dto.getEmail() + " exists!");
        var user = userMapper.toUser(dto);
        var savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }
    public List<User> findAll(Role role){
        return userRepository.findAllByRole(role);
    }
}
