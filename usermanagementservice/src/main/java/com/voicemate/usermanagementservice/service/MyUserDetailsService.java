package com.voicemate.usermanagementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db1entity.User;
import com.voicemate.usermanagementservice.repository.db1repo.UserRepository;
import com.voicemate.usermanagementservice.securityconfig.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userRepository.findByUsername(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			throw new UsernameNotFoundException(username + " Not Found.");
		}
		return new UserPrincipal(user);

	}

}
