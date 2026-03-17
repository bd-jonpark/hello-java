package com.demo.app.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

// A03:2021 - Reflected XSS
// Attack: GET /api/greet?name=<script>alert(1)</script>
@RestController
public class XssController {

    @GetMapping(value = "/api/greet", produces = MediaType.TEXT_HTML_VALUE)
    public String greet(@RequestParam String name) {
        // VULNERABLE: user input reflected directly in HTML response
        return "<h1>Hello, " + name + "!</h1>";
    }
}
