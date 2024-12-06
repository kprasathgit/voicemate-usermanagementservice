package com.voicemate.usermanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.User;
import com.voicemate.usermanagementservice.repository.UserRepository;
import com.voicemate.usermanagementservice.repositorycustom.UserRepositoryCustom;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	public Result findByEmail(String email) throws Exception {
		return userRepositoryCustom.findByEmail(email);
	}

	@Transactional
	public List<User> saveUsers(List<User> listUsers) {
		return userRepository.saveAll(listUsers);
	}

}
