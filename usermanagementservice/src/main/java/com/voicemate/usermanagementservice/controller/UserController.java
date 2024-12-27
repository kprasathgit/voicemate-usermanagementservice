package com.voicemate.usermanagementservice.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.voicemate.usermanagementservice.entities.db1entity.User;
import com.voicemate.usermanagementservice.service.UserService;

@RestController
@RequestMapping(value = "user")
@CrossOrigin(origins = "*")
class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/getuserbyemail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) {

		try {
			User user = userService.findByEmail(email);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}

	}

	@PostMapping("/saveuser")
	public ResponseEntity<List<User>> saveUser(@RequestBody List<User> listUsers) {
		try {

			for (User user : listUsers) {
				user.setCreatedat(LocalDateTime.now());
			}
			return new ResponseEntity<List<User>>(userService.saveUsers(listUsers), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}

}
