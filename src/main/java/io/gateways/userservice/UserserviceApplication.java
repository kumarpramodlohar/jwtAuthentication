package io.gateways.userservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@PropertySource("classpath:application.properties")
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	

//	@Bean
//	CommandLineRunner run() {
//		return args -> {
//				try {
//				      File myObj = new File("cookie.txt");
//				      if (myObj.createNewFile()) {
//				        System.out.println("File created: " + myObj.getName());
//				      } else {
//				        System.out.println("File already exists.");
//				      }
//				    } catch (IOException e) {
//				      System.out.println("An error occurred.");
//				      e.printStackTrace();
//				    }
//
//		};
//	}
	
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
