package com.demo.app.web;

import org.springframework.web.bind.annotation.*;
import java.util.*;

// A01:2021 - Broken Access Control
// Attack: GET /api/admin?role=admin
@RestController
public class AuthBypassController {

    @GetMapping("/api/admin")
    public Map<String, Object> admin(@RequestParam String role) {
        Map<String, Object> response = new LinkedHashMap<>();

        // VULNERABLE: access control based on client-supplied parameter
        if ("admin".equals(role)) {
            response.put("status", "granted");
            response.put("secret", "FLAG{broken_access_control}");
            response.put("db_password", "super_secret_db_pass");
            response.put("api_key", "sk-live-1234567890abcdef");
        } else {
            response.put("status", "denied");
            response.put("message", "Admin role required");
        }
        return response;
    }
}
