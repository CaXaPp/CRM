package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.EmailExistsException;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.UserMapper;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public UserDto createUser(RegisterFormDto dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailExistsException("User with email " + dto.getEmail() + " exists!");
        var user = userMapper.toUser(dto);
        var savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public UserDto getUserById(Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        return userMapper.toUserDto(user);
    }

    public List<User> findAll(Role role){
        return userRepository.findAllByRole(role);
    }

    public List<UserDto> findAllByUser(User user) {
        List<User> users;
        if ("Сотрудник".equals(user.getRole().getName()))
            users = List.of(user);
        else
            users = userRepository.findAllEmployees();
        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto mapToDto(User user) {
        return userMapper.toUserDto(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
