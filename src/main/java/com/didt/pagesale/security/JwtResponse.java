package com.didt.pagesale.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String jwtToken;
    private String username;

}
