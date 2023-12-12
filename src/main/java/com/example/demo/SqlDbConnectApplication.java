package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/api")
public class SqlDbConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlDbConnectApplication.class, args);
    }

}
