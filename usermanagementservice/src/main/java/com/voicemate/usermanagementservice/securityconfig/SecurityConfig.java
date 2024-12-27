package com.voicemate.usermanagementservice.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/user/saveuser", "/product/saveproduct")
						.permitAll() // Public
						// routes
						.anyRequest().authenticated() // Require authentication for other routes

				).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// .formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());

		return http.build();

	}

	/**
	 * By default spring security have UserDetails interface implemented by User
	 * class, which fetches username and password from application.properties
	 * file.But when we want to load username and password from database we need to
	 * rewrite UserDetailsService which is an interface.Since we cannot create
	 * object for an interface and return,we create object of a class which
	 * implements interface(i.e UserDetailsService).So,here
	 * InMemoryUserDetailsManager class implements UserDetailsManager interface
	 * which extends UserDetailsService interface.by this way we can use methods
	 * from UserDetailsService and UserDetailsManager and can return object of it.
	 */

	/**
	 * userDetailsService => this methos works .But its kind of Harcoding.
	 */
//	@Bean
//	UserDetailsService userDetailsService() {
//
//		UserDetails userDetails = User.withDefaultPasswordEncoder().username("HI").password("Hello").build();
//		return new InMemoryUserDetailsManager(userDetails);
//	}

	/**
	 * By default Spring Security dependency uses Authentication Provider to verify
	 * login details (username and password).Here,we are going to rewrite to connect
	 * to database to verify user details
	 */

	@Bean
	AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setUserDetailsService(userDetailsService);
		return provider;

	}

}

/** TO PERMIT ALL REQUEST */
/*
 * http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not
 * recommended for production) .authorizeHttpRequests(auth -> auth
 * .anyRequest().permitAll() // Permit all requests ); return http.build();
 */

/**
 * Non lamda expression for http.csrf(csrf -> csrf.disable()) ;
 */
/*
 * Customizer<CsrfConfigurer<HttpSecurity>> csCustomizer = new
 * Customizer<CsrfConfigurer<HttpSecurity>>() {
 * 
 * @Override public void customize(CsrfConfigurer<HttpSecurity> customizer) {
 * 
 * customizer.disable(); } };
 */
