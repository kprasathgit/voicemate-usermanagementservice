package com.voicemate.usermanagementservice.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // marks the class an entity that will be mapped to table in the database
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, length = 25)
	private String firstname;

	private String lastname;

	private String password; // To be encrypted

	private String oauth2provider;

	private String oauth2providerid;

	private boolean is2faenabled;

	private String phonenumber;

	private String twofactorauthtoken;

	@Column(nullable = false)
	private LocalDateTime createdat;

	@Column(nullable = false)
	private LocalDateTime updatedat;

}
