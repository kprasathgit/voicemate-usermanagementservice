package com.voicemate.usermanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.common.JwtUtils;
import com.voicemate.usermanagementservice.entities.db1entity.User;
import com.voicemate.usermanagementservice.repository.db1repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public User findByUserName(String username) throws Exception {
		return userRepository.findByUsername(username);

	}

	@Transactional
	public List<User> saveUsers(List<User> listUsers) {

		for (User user : listUsers) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		return userRepository.saveAll(listUsers);
	}

	/*
	 * Since we had override the authentication manager,it will contact
	 * authentication provider which was also override by us .That authentication
	 * provider will do the balance verification process from our database as we
	 * mentioned during bean creation.(refer security config class.)
	 */
	public String verifyUser(User user) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());

		}
		return "Failed";
	}

}
