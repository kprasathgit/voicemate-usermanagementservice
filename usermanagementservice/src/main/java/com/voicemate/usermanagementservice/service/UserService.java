package com.voicemate.usermanagementservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.entities.User;
import com.voicemate.usermanagementservice.model.UserPojo;
import com.voicemate.usermanagementservice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<UserPojo> findByEmail(String email) throws Exception {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public List<User> saveUsers(List<User> listUsers) {
		return userRepository.saveAll(listUsers);
	}

}
