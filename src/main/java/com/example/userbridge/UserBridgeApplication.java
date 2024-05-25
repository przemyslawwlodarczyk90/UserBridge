package com.example.userbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class UserBridgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserBridgeApplication.class, args);
    }
}

