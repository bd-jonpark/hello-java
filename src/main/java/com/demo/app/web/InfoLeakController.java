package com.demo.app.web;

import org.springframework.web.bind.annotation.*;
import java.util.*;

// A05:2021 - Security Misconfiguration / Information Leak
// Test: GET /api/debug
@RestController
public class InfoLeakController {

    @GetMapping("/api/debug")
    public Map<String, Object> debug() {
        Map<String, Object> info = new LinkedHashMap<>();

        // VULNERABLE: exposes system internals
        info.put("java.version", System.getProperty("java.version"));
        info.put("os.name", System.getProperty("os.name"));
        info.put("os.arch", System.getProperty("os.arch"));
        info.put("user.name", System.getProperty("user.name"));
        info.put("user.dir", System.getProperty("user.dir"));
        info.put("java.home", System.getProperty("java.home"));

        // VULNERABLE: exposes environment variables (may contain secrets)
        Map<String, String> envSubset = new LinkedHashMap<>();
        for (Map.Entry<String, String> e : System.getenv().entrySet()) {
            envSubset.put(e.getKey(), e.getValue());
        }
        info.put("env", envSubset);

        return info;
    }
}
