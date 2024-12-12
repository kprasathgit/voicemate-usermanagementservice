package com.voicemate.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApigatewayserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayserviceApplication.class, args);
	}

	@GetMapping("/apigatewayservice")
	public String greet() {
		return "GATEWAY SERVICE STARTED";
	}

}
