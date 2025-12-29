package com.example.crm.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserCreateRequestDto {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
