package com.demo.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public DataInitializer(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) {
        jdbc.execute("INSERT INTO users (username, password, email, role) VALUES ('admin', 'admin123', 'admin@example.com', 'admin')");
        jdbc.execute("INSERT INTO users (username, password, email, role) VALUES ('user1', 'password1', 'user1@example.com', 'user')");
        jdbc.execute("INSERT INTO users (username, password, email, role) VALUES ('user2', 'password2', 'user2@example.com', 'user')");
        jdbc.execute("INSERT INTO users (username, password, email, role) VALUES ('guest', 'guest', 'guest@example.com', 'guest')");
    }
}
