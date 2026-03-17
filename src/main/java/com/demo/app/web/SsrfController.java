package com.demo.app.web;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.*;

// A10:2021 - Server-Side Request Forgery (SSRF)
// Attack: GET /api/fetch?url=http://169.254.169.254/latest/meta-data/
@RestController
public class SsrfController {

    @GetMapping("/api/fetch")
    public String fetch(@RequestParam String url) throws Exception {
        // VULNERABLE: user-supplied URL fetched without validation
        URLConnection conn = new URL(url).openConnection();
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
}
