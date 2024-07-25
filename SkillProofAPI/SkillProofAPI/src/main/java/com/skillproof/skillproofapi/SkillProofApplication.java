package com.skillproof.skillproofapi;

import com.skillproof.skillproofapi.enums.RoleType;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.repositories.user.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@SpringBootApplication
@ComponentScan(basePackages = {"com.skillproof.skillproofapi.*"})
@EntityScan(basePackages = {"com.skillproof.skillproofapi.*"})
@EnableJpaRepositories(basePackages = {"com.skillproof.skillproofapi.*"})
public class SkillProofApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillProofApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            User admin = userRepository.getUserByUsername("admin@mail.com");
            User employee = userRepository.getUserByUsername("employee@mail.com");
            if (ObjectUtils.isEmpty(admin)) {
                userRepository.createUser(createUser("admin@mail.com",
                        encoder.encode("admin"),
                        "admin", "admin", RoleType.ADMIN));
            }
            if (ObjectUtils.isEmpty(employee)) {
                userRepository.createUser(createUser("employee@mail.com",
                        encoder.encode("employee"),
                        "employee", "employee", RoleType.EMPLOYEE));
            }
        };
    }

    private User createUser(String userName, String password, String firstName, String lastName, RoleType role) {
        User user = new User();
        String userId = RandomStringUtils.randomAlphabetic(20);
        user.setId(userId);
        user.setEmailAddress(userName);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setCity("Something");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return user;
    }

}
