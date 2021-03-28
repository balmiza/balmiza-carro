package com.carros2.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	private final Log logger = LogFactory.getLog(WebSecurityConfigurerAdapter.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception{

		this.logger.debug("Using default configure(HttpSecurity). "
				+ "If subclassed this will potentially override subclass configure(HttpSecurity).");
		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
		http.httpBasic();
		http.csrf().disable();
	
	} 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
		
//		auth
//		  	.inMemoryAuthentication().passwordEncoder(encoder)
//		  	.withUser("user").password(encoder.encode("user")).roles("USER")
//		  	.and()
//		  	.withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
	
	} 
	
	
}
























