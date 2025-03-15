package com.aig.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aig.backend.services.*;

@SpringBootApplication
public class AigApp {
    public static void main(String[] args) {
        SpringApplication.run(AigApp.class, args);
    }
}
