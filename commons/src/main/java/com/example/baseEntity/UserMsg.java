package com.example.baseEntity;

import lombok.Data;

@Data
public class UserMsg {
    private String token;
    private String username;
    private String role;
}
