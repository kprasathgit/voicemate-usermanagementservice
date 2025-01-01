package com.voicemate.usermanagementservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

@RestController
@CrossOrigin(origins = "*")
@SessionScope
public class GreetController {

	@GetMapping("/")
	private String greet() {
		return "USER MANAGEMENT SERVICE STARTED";
	}

	@GetMapping("/login-success")
	public String loginSuccess() {
	    return "GitHub Login Successful!";
	}

//	@GetMapping("/github")
//	private String gitHubLogin() {
//		return "GIT LOGIN SUCCESSFUL";
//	}
}
