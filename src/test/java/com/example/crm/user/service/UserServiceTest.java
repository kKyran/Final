package com.example.crm.user.service;

import com.example.crm.user.dto.UserCreateRequestDto;
import com.example.crm.user.dto.UserResponseDto;
import com.example.crm.user.entity.Role;
import com.example.crm.user.entity.User;
import com.example.crm.user.mapper.UserMapper;
import com.example.crm.user.repository.RoleRepository;
import com.example.crm.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        when(userMapper.toUserResponseDto(any(User.class))).thenReturn(new UserResponseDto());

        var result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userMapper).toUserResponseDto(any(User.class));
    }

    @Test
    void createUser_success() {
        UserCreateRequestDto request = new UserCreateRequestDto();
        request.setUsername("testuser");
        request.setEmail("test@test.com");
        request.setPassword("password");
        request.setRoles(Set.of("ROLE_USER"));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role(1L, "ROLE_USER")));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(userMapper.toUserResponseDto(any(User.class))).thenReturn(new UserResponseDto());


        userService.createUser(request);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_usernameExists() {
        UserCreateRequestDto request = new UserCreateRequestDto();
        request.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(request));
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
