package com.voicemate.usermanagementservice.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
}

//TO PERMIT ALL REQUEST
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
