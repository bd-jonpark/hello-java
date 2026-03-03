package com.blackduck.app.web;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        Map<String, String> status = new LinkedHashMap<>();
        status.put("status", "UP");
        status.put("app", "hello-java");
        status.put("version", "1.0");
        return status;
    }

    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("app", "hello-java — Vulnerable Web App");
        info.put("description", "OWASP Top 10 demo endpoints for SAST/DAST/IAST testing");

        List<Map<String, String>> endpoints = new ArrayList<>();
        endpoints.add(endpoint("GET", "/api/health", "Health check"));
        endpoints.add(endpoint("GET", "/api/users?name=admin", "A03 SQL Injection"));
        endpoints.add(endpoint("GET", "/api/greet?name=World", "A03 Reflected XSS"));
        endpoints.add(endpoint("GET", "/api/ping?host=localhost", "A03 Command Injection"));
        endpoints.add(endpoint("GET", "/api/file?name=test.txt", "A01 Path Traversal"));
        endpoints.add(endpoint("GET", "/api/fetch?url=http://example.com", "A10 SSRF"));
        endpoints.add(endpoint("GET", "/api/admin?role=user", "A01 Broken Access Control"));
        endpoints.add(endpoint("GET", "/api/encrypt?data=hello", "A02 Weak Crypto"));
        endpoints.add(endpoint("GET", "/api/debug", "A05 Info Leak"));
        endpoints.add(endpoint("POST", "/api/xml", "A05 XXE (XML body)"));
        info.put("endpoints", endpoints);

        return info;
    }

    private Map<String, String> endpoint(String method, String path, String desc) {
        Map<String, String> ep = new LinkedHashMap<>();
        ep.put("method", method);
        ep.put("path", path);
        ep.put("description", desc);
        return ep;
    }
}
