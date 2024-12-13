package com.daw.icomputer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableJpaRepositories("com.daw.icomputer.repository")  
@EntityScan("com.daw.icomputer.model") 
public class IcomputerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcomputerApplication.class, args);
    }
}
