package io.gateways.userservice.service;

import java.util.List;



import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.User;

public interface UserService {

	User saveUser(User user);
	
	Role saveRole(Role role);
	
	void addRoleToUser(String username,String roleName);
	
	User getUser(String username);
	
	List<User>getUsers();
	
}
