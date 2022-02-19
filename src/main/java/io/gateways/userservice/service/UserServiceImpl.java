package io.gateways.userservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.SerialUpdate;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.repo.RoleRepo;
import io.gateways.userservice.repo.SerialUpdateRepo;
import io.gateways.userservice.repo.UserRepo;
import io.gateways.userservice.repo.UserdetailsRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserdetailsRepo userdetailsrepo;
	
	@Autowired
	private SerialUpdateRepo serialUpdateRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);

		if (user == null) {
			log.error("User not found in the database ");
			throw new UsernameNotFoundException("User not found in the database");
		} else {

			log.info("User found in the database: {}", username);
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		System.out.println("Load Roles by user name " + user.getRoles().toString());
		user.getRoles().forEach(role -> {

			authorities.add(new SimpleGrantedAuthority(role.getName()));
			log.info("Authorities ", role.getName());
		});

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	@Override
	public User saveUser(User user) {

		log.info("Saving new user {} to the database", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {

		log.info("Adding  role {} to user {} ", roleName, username);

		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);

		user.getRoles().add(role);

	}

	@Override
	public User getUser(String username) {

		log.info("Fetching user {} ", username);

		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {

		log.info("Fetching All users {} ");

		return userRepo.findAll();
	}

	@Override
	public io.gateways.userservice.domain.UserDetails registrationSave(
			io.gateways.userservice.domain.UserDetails userdetails) {
		
		Calendar cal=Calendar.getInstance();
		int yr=cal.get(Calendar.YEAR);
		int mth=cal.get(Calendar.MONTH);
		int dt=cal.get(Calendar.DATE);
		
		Integer serialNo=serialUpdateRepo.getserial("user");
		
		
		log.info("serialNo............................",serialNo);
		
		
		
		return userdetailsrepo.save(userdetails);
	}

}
