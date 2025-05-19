package edu.unlp.medflash.med_unp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
    exclude = { SecurityAutoConfiguration.class }
)

public class MedUnlpApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedUnlpApiApplication.class, args);
    }
}