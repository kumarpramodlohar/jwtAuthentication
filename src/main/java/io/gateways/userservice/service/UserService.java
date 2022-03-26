package io.gateways.userservice.service;

import java.util.List;

import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.StockDetails;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.domain.UserDetails;
import io.gateways.userservice.domain.Wallet;
import io.gateways.userservice.domain.WatchlistBean;

public interface UserService {

	User saveUser(User user);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	User getUser(String username);

	List<User> getUsers();

	UserDetails registrationSave(UserDetails userdetails);

	String signup(User user);
	
	String addWatchlist(WatchlistBean watchlistBean);
	
	void userDeactivate(String client_id);
	
	List<StockDetails> getWatchlist(String username);

	List<Wallet> getWallet(String username);
	
}
