package com.voicemate.usermanagementservice.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		/**
		 * BY DEFAULT USER PASSWORD AUTHENTICATION FILTER(UPAF) ACTIVATED FIRST.IF WE WE
		 * WANT TO ADD ANY OTHER FILTER BEFORE USER PASSWORD AUTHENTICATION FILTER
		 * (eg.JWT Authentication) THEN WE WANT TO MENTION IT.
		 */

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/user/saveuser", "/product/saveproduct", "/user/verifyuser")
								/*
								 * By permitting,authenication will become optional for mentioned routes.For
								 * Example,If we use Basic Auth,in that case username and password will be
								 * optional.Even if we pass correct user name and password it works.But not
								 * mandatory.
								 */
								.permitAll().anyRequest().authenticated() // Require authentication for other routes

				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				 .formLogin(Customizer.withDefaults())				
				   .oauth2Login(Customizer.withDefaults()
	                ).
				
				httpBasic(Customizer.withDefaults())
				// Here we are saying before UserPasswordAuthentication use jwtFilter.
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
	 * 
	 * @throws Exception
	 */
//	@Bean
//	UserDetailsService userDetailsService() {
//
//		UserDetails userDetails = User.withDefaultPasswordEncoder().username("HI").password("Hello").build();
//		return new InMemoryUserDetailsManager(userDetails);
//	}

	/**
	 * Spring Security hits authentication manager first then authentication manager
	 * will talk to Authentication Provider..In our case we overrided authentication
	 * provider..
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * By default Spring Security dependency uses Authentication Provider to verify
	 * login details (username and password).Here,we are going to rewrite to connect
	 * to database to verify user details
	 */

	@Bean
	AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;

	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}

//JOT or JWT - JSON web Token..
/*
 * Its like having an entry card for any shop.The card holds neccesaary details
 * like username,joineddate and expiredat etc.. with a signature to verify..so
 * that any request can be verified.Using this signature and other details we
 * can achieve accountability like one is a valid user without storing users
 * data and checks it everytime.. Signature is important ..Refer how to use it.
 */

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

//
//spring.security.oauth2.client.registration.github.client-id=Ov23liTt5nbK0HgsZ1CC
//spring.security.oauth2.client.registration.github.client-secret=c12db47f660cd8b362c0d50d46c4c495691a904e
//
//spring.security.oauth2.client.registration.google.client-id=470422928140-qn6tf5ltoehpua9uasvl0585o2kf0npf.apps.googleusercontent.com
//spring.security.oauth2.client.registration.google.client-secret=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
//

