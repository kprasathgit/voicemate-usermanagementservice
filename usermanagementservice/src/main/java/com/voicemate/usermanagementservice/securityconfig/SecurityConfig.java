package com.voicemate.usermanagementservice.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/product/saveproduct").permitAll().anyRequest().authenticated() // Permit
																														// all
																														// requests
				).httpBasic(org.springframework.security.config.Customizer.withDefaults());
		return http.build();

	}
}

//TO PERMIT ALL REQUEST
/*
 * http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not
 * recommended for production) .authorizeHttpRequests(auth -> auth
 * .anyRequest().permitAll() // Permit all requests ); return http.build();
 */
