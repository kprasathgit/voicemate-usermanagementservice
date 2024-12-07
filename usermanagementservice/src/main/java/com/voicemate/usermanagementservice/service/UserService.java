package com.voicemate.usermanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db1entity.User;
import com.voicemate.usermanagementservice.repository.db1repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Result findByEmail(String email) throws Exception {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public List<User> saveUsers(List<User> listUsers) {
		return userRepository.saveAll(listUsers);
	}

}
