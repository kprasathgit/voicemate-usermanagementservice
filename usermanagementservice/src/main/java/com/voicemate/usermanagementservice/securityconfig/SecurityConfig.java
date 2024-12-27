package com.voicemate.usermanagementservice.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/product/saveproduct").permitAll().anyRequest().authenticated() // Permit
																														// all
																														// requests
				).httpBasic(org.springframework.security.config.Customizer.withDefaults());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	/**
	 * By default spring security have UserDetails interface implemented by User
	 * class, which fetches username and password from application.properties file.But
	 * when we want to load username and password from database we need to rewrite
	 * UserDetailsService which is an interface.Since we cannot create object for an
	 * interface and return,we create object of a class which implements
	 * interface(i.e UserDetailsService).So,here InMemoryUserDetailsManager class
	 * implements UserDetailsManager interface which extends UserDetailsService
	 * interface.by this way we can use methods from UserDetailsService and
	 * UserDetailsManager and can return object of it.
	 */

	@Bean
	UserDetailsService userDetailsService() {

		return new InMemoryUserDetailsManager();
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
