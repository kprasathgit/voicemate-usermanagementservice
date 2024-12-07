package com.voicemate.usermanagementservice.repository.db1repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db1entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Result findByEmail(String email) throws Exception;
}
