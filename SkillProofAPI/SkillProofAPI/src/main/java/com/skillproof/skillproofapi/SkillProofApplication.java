package com.skillproof.skillproofapi;

import com.skillproof.skillproofapi.enumerations.RoleType;
import com.skillproof.skillproofapi.model.entity.Role;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.repositories.RoleDao;
import com.skillproof.skillproofapi.repositories.UserDao;
import com.skillproof.skillproofapi.repositories.role.RoleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
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
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}

	@Bean
	CommandLineRunner initDatabase(UserDao userDao, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
		return args -> {
			if (userDao.findByRole(RoleType.ADMIN).size() == 0) {
				Role admin_role = roleRepository.createRole(createRole(RoleType.ADMIN));
				Role employee_role = roleRepository.createRole(createRole(RoleType.EMPLOYEE));
				roleRepository.createRole(createRole(RoleType.EMPLOYER));
				userDao.save(createUser("admin@mail.com",
						encoder.encode("012345"),
						"admin", "admin", admin_role));
				userDao.save(createUser("employee@mail.com",
						encoder.encode("012345"),
						"employee", "employee", employee_role));
			}
		};
	}

	private Role createRole(RoleType roleType) {
		Role role = new Role();
		if (roleType == RoleType.ADMIN) {
			role.setDescription("Role for Admin Access");
		} else {
			String desc = roleType == RoleType.EMPLOYEE ? "Role for Employee" : "Role for Employer";
			role.setDescription(desc);
		}
		role.setName(roleType);
		role.setCreatedDate(LocalDateTime.now());
		role.setUpdatedDate(LocalDateTime.now());
		return role;
	}

	private User createUser(String userName, String password, String firstName, String lastName, Role role) {
		User user = new User(userName, password, firstName, lastName);
		user.setRole(role);
		user.setCreatedDate(LocalDateTime.now());
		user.setUpdatedDate(LocalDateTime.now());
		return user;
	}

}
