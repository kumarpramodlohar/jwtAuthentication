package io.gateways.userservice.service;

import java.util.List;

import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.SerialUpdate;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.domain.UserDetails;

public interface UserService {

	User saveUser(User user);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	User getUser(String username);

	List<User> getUsers();

	UserDetails registrationSave(UserDetails userdetails);

	String signup(User user);
	

}
