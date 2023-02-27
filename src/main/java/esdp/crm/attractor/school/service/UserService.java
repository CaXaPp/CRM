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

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    public List<UserDto> getAllEmployee() {
        return userRepository.findAllByRole_Value("ROLE_EMPLOYEE").stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    public List<UserDto> getAllNotInAdmin() {
        return userRepository.findAllUsersNotInAdmin().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    public UserDto save(RegisterFormDto dto) throws EntityExistsException {
        if (userRepository.existsByEmail(dto.getEmail())) throw new EntityExistsException("Пользователь с такой почтой существует!");
        var user = userMapper.toNewUser(dto);
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
            users = userRepository.findAllByRole_Value("ROLE_EMPLOYEE");
        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public void editUser(RegisterFormDto dto) {
        userMapper.toForm(userRepository.save(userMapper.toOldUser(dto)));
    }

    public RegisterFormDto getUserForm(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        return userMapper.toForm(user.get());
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto mapToDto(User user) {
        return userMapper.toUserDto(user);
    }

    public UserDto findByEmail(String email) {
        return userMapper.toUserDto(userRepository.findByEmail(email));
    }

}
