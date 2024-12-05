package com.voicemate.usermanagementservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.model.UserPojo;
import com.voicemate.usermanagementservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<UserPojo> findByEmail(String email) throws Exception{
		return userRepository.findByEmail(email);
	}
}
