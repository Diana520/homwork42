package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private UserSecurityDetailsService detailsService;
	
	@Autowired
	public SecurityConfig(UserSecurityDetailsService detailsService) {
		super();
		this.detailsService = detailsService;
	}


	//AuthenticationManager - бин компонента, который управляет процессом проверки того, 
	//что такой пользователь есть
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//UserDetails - описывает одного пользователя, понятного Spring Security
		UserDetails user1 = User.builder()
				.username("user")
				.password(passwordEncoder().encode("user"))
				.authorities("ROLE_USER")
				//.roles("USER")
				.build();
		
		
		UserDetails user2 = User.builder()
				.username("Diana")
				.password(passwordEncoder().encode("twin"))
				.authorities("ROLE_ADMIN")
				//.roles("ADMIN")
				.build();
		
		UserDetails user3 = User.builder()
				.username("Manager")
				.password(passwordEncoder().encode("manager"))
				.authorities("ROLE_MANAGER")
				.build();
		auth.inMemoryAuthentication().withUser(user1).withUser(user2).withUser(user3);
	}
	
	
	//AuthenticationProvider-предоставляет инфу для authentication
/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//ак по username найти конкректного пользователя UserDetails
		provider.setUserDetailsService(detailsService);
		provider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(provider);
	}*/
	
	//UserDetailsService - по юзернейму получает UserDetails
	//авторизация - доступ, согласно ролям
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()//какие пользователи имеют доступ к url
		.antMatchers("/").permitAll()//какие пользователи имеют доступ к url (все могут видеть index)
		.antMatchers("/notes/info").authenticated()//разрешено пользователям, которые вошли
		.antMatchers("/notes/edit").authenticated()
		.antMatchers("/admin_page/**").hasAuthority("ROLE_ADMIN")//разрешено только админу
		.antMatchers("/notes/delete/**").hasAuthority("ROLE_MANAGER")
		//.antMatchers("/notes/delete/**").hasAuthority("ROLE_ADMIN")
		.and()
		.formLogin()//включение дефолтной формы
		.and() 
		.logout() //логаут
		.and()
		.csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
