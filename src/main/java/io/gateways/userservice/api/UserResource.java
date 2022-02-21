package io.gateways.userservice.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.SerialUpdate;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.domain.UserDetails;
import io.gateways.userservice.service.UserService;
import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")

public class UserResource extends Exception {

	private static final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@GetMapping("/test")
	public String apiTest() {
		System.out.println("In test api");
		return "connected";
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {

		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/user/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {

		// URI
		// uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

		// return ResponseEntity.created(uri).body(userService.saveUser(user));

		return ResponseEntity.ok().body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		// URI
		// uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

		// return ResponseEntity.created(uri).body(userService.saveRole(role));
		return ResponseEntity.ok().body(userService.saveRole(role));

	}

	@PostMapping("/role/addtoUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {

		userService.addRoleToUser(form.getUsername(), form.getRoleName());

		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/registration/save")
	public ResponseEntity<?> registrationSave(@RequestBody UserDetails userdetails
			) {

		// URI
		// uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

		// return ResponseEntity.created(uri).body(userService.saveUser(user));
		
		System.out.println("Hiiiiiiii...................");

		return ResponseEntity.ok().body(userService.registrationSave(userdetails));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user
			) {

		// URI
		// uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

		// return ResponseEntity.created(uri).body(userService.saveUser(user));
		
		System.out.println("Hiiiiiiii...................");

		return ResponseEntity.ok().body(userService.signup(user));
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws StreamWriteException, DatabindException, IOException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.getUser(username);

				String access_token = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);

				// response.setHeader("access_token", access_token);
				// response.setHeader("refresh_token", refresh_token);

				Map<String, String> tokens = new HashMap<>();

				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);

				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception exception) {

				response.setHeader("error", exception.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				// response.sendError(HttpStatus.FORBIDDEN.value());

				Map<String, String> error = new HashMap<>();

				error.put("error_message", exception.getMessage());

				response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);

				new ObjectMapper().writeValue(response.getOutputStream(), error);

			}

		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}

	public static Logger getLog() {
		return log;
	}

}

@Data
class RoleToUserForm {
	private String username;
	private String roleName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
