package io.gateways.userservice.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.gateways.userservice.domain.Role;
import io.gateways.userservice.domain.StockCodes;
import io.gateways.userservice.domain.StockDetails;
import io.gateways.userservice.domain.StockTransactionBuy;
import io.gateways.userservice.domain.StockTransactionSell;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.domain.Wallet;
import io.gateways.userservice.domain.WatchlistBean;
import io.gateways.userservice.repo.RoleRepo;
import io.gateways.userservice.repo.SerialUpdateRepo;
import io.gateways.userservice.repo.StockCodesRepo;
import io.gateways.userservice.repo.StockTransactionBuyRepo;
import io.gateways.userservice.repo.StockTransactionSellRepo;
import io.gateways.userservice.repo.UserRepo;
import io.gateways.userservice.repo.UserdetailsRepo;
import io.gateways.userservice.repo.WalletRepo;
import io.gateways.userservice.repo.WatchlistRepo;
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
	@Autowired
	private StockCodesRepo stockCodesRepo;
	
	@Autowired
	private WalletRepo walletRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private WatchlistRepo watchlistRepo;

	@Autowired
	private StockTransactionBuyRepo stockTransactionRepo;
	
	@Autowired
	private StockTransactionSellRepo stockTransactionSellRepo;
	
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
		long milliseconds = System.currentTimeMillis();
		LocalDateTime Date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
		user.setCreated_on(Date);
		user.setClient_id(client_id);
		user.setStatus("Y");
		User local = null;
		

		
		local = userRepo.findByUsername(user.getUsername());
		

		
		
		if(local != null) {
			
			response = "Failed";
		}else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			//ArrayList<Role> roles = new ArrayList<Role>();
			//user.setRoles(roles);
			userRepo.save(user);
			
			Wallet userwallet = new Wallet();
			
			
			User localUser = userRepo.findByUsername(user.getUsername());
			//System.out.println("User details ==============="+localUser.getName());
			//System.out.println("User details ==============="+localUser.getUsername());
			//System.out.println("User details ==============="+localUser.getPassword());
			userwallet.setUsername(localUser.getUsername());
			userwallet.setBalance(20000);
			userwallet.setClient_id(client_id);
			walletRepo.save(userwallet);
			
			Role role = new Role();
			//role.setName("Role User");
			if(user.getUsername().equals("admin")) {
				role = roleRepo.findByName("ROLE_ADMIN");
			}
			else {
				role = roleRepo.findByName("ROLE_USER");
			}
			
			user.getRoles().add(role);
			
			response = "User Addition successfull";
		}
		
		

		
		return response;
	}
	
	@Override
	public List<StockDetails> getWatchlist(String username) {
		
		List<StockDetails> list = new ArrayList<StockDetails>();
		list = em.createNativeQuery("select s.id,s.Exch,s.ExchType,s.Symbol,s.StrikePrice,s.OptionType,s.Expiry from onlinetradingdb.stock_details s join onlinetradingdb.user_watchlist u on u.symbol = s.Symbol where u.username = '"+username+"'",StockDetails.class).getResultList();
		
		return list;
	}
	
	@Override
	public String addWatchlist(WatchlistBean watchlistBean) {
		// TODO Auto-generated method stub
		watchlistBean.setStatus("Y");
		String message = "";
		WatchlistBean check = null;
		check = watchlistRepo.getWatchListByUsernameSymbol(watchlistBean.getUsername(), watchlistBean.getSymbol());
		if(check == null) {
		watchlistRepo.save(watchlistBean);
		message = "success";
		}else {
			message = "Already Exists";
		}
		return message;
	}

	@Override
	public void userDeactivate(String client_id) {
		// TODO Auto-generated method stub
		userRepo.userDeactivate(client_id);
		
	}
	
	@Override
	public List<Wallet> getWallet(String username) {
		// TODO Auto-generated method stub
		log.info("Fetching user {} ", username);

		return walletRepo.findByUsername(username);
	}

	@Override
	public void updateWallet(String client_id, int balance) {
		// TODO Auto-generated method stub
		System.out.println("client_id.............................."+client_id);
		System.out.println("client_id.............................."+balance);
		userRepo.updateWallet(client_id,balance);
	}
	 @Override
		public List<StockTransactionBuy> getBuydetails(String username) {
			log.info("Fetching Buy Details user {} ", username);

			return stockTransactionRepo.findBuydetailsByUsername(username);
		}

		@Override
		public List<StockTransactionSell> getSelldetails(String username) {
			log.info("Fetching Sell Details user {} ", username);

			return stockTransactionSellRepo.findSelldetailsByUsername(username);
		}

		@Override
		public String getPortfolio(String username) {
			// TODO Auto-generated method stub
			HashMap<String,Integer> map = new HashMap<String,Integer>();
			
			map.put("TotalBought", stockTransactionRepo.getTotalSpent(username));
			map.put("TotalSold", stockTransactionSellRepo.getTotalSold(username));
			String json = "";
			try {
				 json = new ObjectMapper().writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
			
			
		}

		@Override
		public List<StockCodes> stockFetchData(String username) {
			// TODO Auto-generated method stub
			return stockCodesRepo.stockFetchData(username);
		}

}
