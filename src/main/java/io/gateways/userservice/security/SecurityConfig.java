package io.gateways.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.gateways.userservice.filter.CustomAuthorizationFilter;
import io.gateways.userservice.filter.CustomeAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	

	@Autowired
	private  UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CustomeAuthenticationFilter customeAuthenticationFilter=new CustomeAuthenticationFilter(authenticationManagerBean());
		customeAuthenticationFilter.setFilterProcessesUrl("/api/login");
		//customeAuthenticationFilter.setFilterProcessesUrl("/api/test");
		/*
		 * http.cors(); http.csrf().disable();
		*/
		
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/login/**","/api/registration/save/**","/api/token/refresh/**","/api/signup/**","/api/test/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/live/market-feed/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/live/stockName/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/live/industry-feed/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/getWatchlist/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/addWatchlist/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/fetchWallet/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/registration/save/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/buyStock/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/updateWallet/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/fetchStockByUser/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");	
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/live/stockCodeSearch/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/live/UserStocksQuantity/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/live/NiftyDatas/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customeAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
		
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {		
		return super.authenticationManagerBean();
		
	}
}
