package com.voicemate.translationservice.controller;

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
		return "TRANSLATION SERVICE STARTED";
	}
}
