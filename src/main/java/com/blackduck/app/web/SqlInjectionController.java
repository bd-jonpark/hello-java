package com.blackduck.app.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// A03:2021 - SQL Injection
// Attack: GET /api/users?name=' OR '1'='1
@RestController
public class SqlInjectionController {

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/api/users")
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUsers(@RequestParam String name) {
        // VULNERABLE: string concatenation in SQL query
        String sql = "SELECT id, username, password, email, role FROM users WHERE username = '" + name + "'";
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> user = new LinkedHashMap<>();
            user.put("id", row[0]);
            user.put("username", row[1]);
            user.put("password", row[2]);
            user.put("email", row[3]);
            user.put("role", row[4]);
            result.add(user);
        }
        return result;
    }
}
