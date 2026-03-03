package com.blackduck.app.web;

import org.springframework.web.bind.annotation.*;
import java.io.*;

// A03:2021 - OS Command Injection
// Attack: GET /api/ping?host=;id
@RestController
public class CommandInjectionController {

    @GetMapping("/api/ping")
    public String ping(@RequestParam String host) throws Exception {
        // VULNERABLE: user input passed directly to shell command
        String command = "ping -c 1 " + host;
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        process.waitFor();
        return output.toString();
    }
}
