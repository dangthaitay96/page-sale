package com.didt.pagesale.security;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
