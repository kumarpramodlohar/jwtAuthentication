package io.gateways.userservice;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.service.UserService;

@SpringBootApplication

public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Pramod Lohar", "pramod", "pramod1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Subhamay Mondal", "subhamay", "subhamay1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Avishto Banerjee", "avishto", "avistho1234", new ArrayList<>()));
			userService.saveUser(new User(null, "ShibShankar Pradhan", "shibShankar", "shibShankar1234", new ArrayList<>()));

			userService.addRoleToUser("pramod", "ROLE_USER");
			userService.addRoleToUser("shibShankar", "ROLE_MANAGER");
			userService.addRoleToUser("shibShankar", "ROLE_USER");
			userService.addRoleToUser("shibShankar", "ROLE_ADMIN");
			userService.addRoleToUser("avishto", "ROLE_ADMIN");
			userService.addRoleToUser("subhamay", "ROLE_USER");

		};
	}

}
