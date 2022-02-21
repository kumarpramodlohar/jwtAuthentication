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
		
		//Remove client id and then make sure that the user logged in client id get submitted
		
		String client_id=null;
		Integer sl_no=0;
		Calendar cal=Calendar.getInstance();
		String yr=Integer.toString(cal.get(Calendar.YEAR));
		String mth=Integer.toString(cal.get(Calendar.MONTH)+1);
		String dt=Integer.toString(cal.get(Calendar.DATE));
		
		sl_no=serialUpdateRepo.getSerial("user");
		sl_no++;
		client_id=yr+""+mth+""+dt+""+Integer.toString(sl_no);
		serialUpdateRepo.updateSerial("user",sl_no);
		
		log.info("Client id is ........................................"+client_id);
		log.info("serialNo............................",sl_no);
		
		
		userdetails.setClient_id(client_id);
		return userdetailsrepo.save(userdetails);
	}
	
	@Override
	public String signup(User user) {
		
		String response = "";
		
		String client_id=null;
		Integer sl_no=0;
		Calendar cal=Calendar.getInstance();
		
		String yr=Integer.toString(cal.get(Calendar.YEAR));
		String mth=Integer.toString(cal.get(Calendar.MONTH)+1);
		String dt=Integer.toString(cal.get(Calendar.DATE));
		
		 sl_no=serialUpdateRepo.getSerial("user");
		sl_no++;
		 client_id=yr+""+mth+""+dt+""+Integer.toString(sl_no);
		 
		serialUpdateRepo.updateSerial("user",sl_no);
		
		user.setClient_id(client_id);
		
		User local = null;
		
//		System.out.println("User details ==============="+user.getName());
//		System.out.println("User details ==============="+user.getUsername());
//		System.out.println("User details ==============="+user.getPassword());
		
		local = userRepo.findByUsername(user.getUsername());
		
//		System.out.println("User details ==============="+local.getName());
//		System.out.println("User details ==============="+local.getUsername());
//		System.out.println("User details ==============="+local.getPassword());
		
//		
		if(local != null) {
			
			response = "User Already Exists";
		}else {
			ArrayList<Role> roles = new ArrayList<Role>();
				user.setRoles(roles);
			userRepo.save(user);
			User localUser = userRepo.findByUsername(user.getUsername());
			System.out.println("User details ==============="+localUser.getName());
			System.out.println("User details ==============="+localUser.getUsername());
			System.out.println("User details ==============="+localUser.getPassword());
			Role role = new Role();role.setName("Role User");
			localUser.getRoles().add(role);
			
			response = "User Addition successfull";
		}
		
		
//		response = "True";
		
		return response;
	}

}
