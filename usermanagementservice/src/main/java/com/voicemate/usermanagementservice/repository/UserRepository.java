package com.voicemate.usermanagementservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voicemate.usermanagementservice.entities.User;
import com.voicemate.usermanagementservice.model.UserPojo;


public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<UserPojo> findByEmail(String email) throws Exception;
	
}
