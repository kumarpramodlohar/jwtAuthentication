package io.gateways.userservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableScheduling
@EnableAsync
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	

//	@Bean
	//CommandLineRunner run(UserService userService) {
	//	return args -> {
		//	userService.saveUser(new User(null,"Administrator","admin","admin","15678910","Y","admin","123456",new ArrayList<>()));
		//	userService.addRoleToUser("admin", "ROLE_ADMIN");
//
//			userService.saveUser(new User(null, "Pramod Lohar", "pramod", "pramod1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "Subhamay Mondal", "subhamay", "subhamay1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "Avishto Banerjee", "avishto", "avistho1234", new ArrayList<>()));
//			userService.saveUser(new User(null, "ShibShankar Pradhan", "shibShankar", "shibShankar1234", new ArrayList<>()));
//
//			userService.addRoleToUser("pramod", "ROLE_USER");
//			userService.addRoleToUser("shibShankar", "ROLE_MANAGER");
//			userService.addRoleToUser("shibShankar", "ROLE_USER");
//			userService.addRoleToUser("shibShankar", "ROLE_ADMIN");
//			userService.addRoleToUser("avishto", "ROLE_ADMIN");
//			userService.addRoleToUser("subhamay", "ROLE_USER");
//
	//	};
	//}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/login").allowedOrigins("*");
			}
		};
	}

	


}
