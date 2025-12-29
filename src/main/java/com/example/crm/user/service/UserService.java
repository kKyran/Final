package com.example.crm.user.service;

import com.example.crm.user.dto.UserCreateRequestDto;
import com.example.crm.user.dto.UserResponseDto;
import com.example.crm.user.entity.Role;
import com.example.crm.user.entity.User;
import com.example.crm.user.mapper.UserMapper;
import com.example.crm.user.repository.RoleRepository;
import com.example.crm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found.")))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
