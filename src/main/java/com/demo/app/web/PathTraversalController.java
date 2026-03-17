package com.demo.app.web;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.*;

// A01:2021 - Path Traversal
// Attack: GET /api/file?name=../../../etc/passwd
@RestController
public class PathTraversalController {

    @GetMapping("/api/file")
    public String readFile(@RequestParam String name) throws Exception {
        // VULNERABLE: user input used directly in file path
        File file = new File("/tmp/uploads/" + name);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
