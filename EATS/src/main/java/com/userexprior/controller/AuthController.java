package com.userexprior.controller;

import com.userexprior.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Normally you'd validate username/password against DB
        if ("user".equals(username) && "pass".equals(password)) {
            return jwtUtil.generateToken(username);
        }
        return "Invalid credentials";
    }
}
