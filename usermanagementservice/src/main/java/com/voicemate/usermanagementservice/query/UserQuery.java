package com.voicemate.usermanagementservice.query;

import org.springframework.stereotype.Component;

@Component
public class UserQuery {

	public String findByEmail() {

		StringBuilder sb = new StringBuilder("");
		sb.append("select * from user as u where email = :email");
		return sb.toString();
	}

}
