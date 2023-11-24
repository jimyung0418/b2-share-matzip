package com.example.deliciouscard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DeliciouscardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliciouscardApplication.class, args);
    }

}
