package com.voicemate.usermanagementservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.server.ResponseStatusException;

import com.voicemate.usermanagementservice.entities.User;
import com.voicemate.usermanagementservice.model.UserPojo;
import com.voicemate.usermanagementservice.service.UserService;

@RestController
@RequestMapping(value = "user")
@CrossOrigin(origins = "*")
@SessionScope
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/getuserbyemail")
	public ResponseEntity<Optional<UserPojo>> getUserByEmail(@RequestParam String email) {

		try {
			return new ResponseEntity<Optional<UserPojo>>(userService.findByEmail(email), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}

	}

	@PostMapping("/saveuser")
	public ResponseEntity<List<User>> saveUser(@RequestBody List<User> listUsers) {
		try {
			return new ResponseEntity<List<User>>(userService.saveUsers(listUsers), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}

}
