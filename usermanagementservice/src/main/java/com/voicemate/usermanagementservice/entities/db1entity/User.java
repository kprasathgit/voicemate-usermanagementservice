package com.voicemate.usermanagementservice.entities.db1entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
	private String username;

	@Column(nullable = false, length = 100)
	private String firstname;

	@Column(length = 100)
	private String lastname;

	@Column(length = 100, nullable = false)
	private String password; // To be encrypted

	@Column(length = 100)
	private String oauth2provider;

	@Column(length = 100)
	private String oauth2providerid;

	@Column(columnDefinition = "BOOLEAN DEFAULT false")
	private boolean is2faenabled;

	@Column(columnDefinition = "varchar(60) DEFAULT NULL")
	private String phonenumber;

	private String twofactorauthtoken;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createddate;

	@Column(nullable = false, columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") // SSSSSS- Microseconds ; SSS - Milliseconds
	private LocalDateTime createdtime;

	@Column(nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
	private LocalDateTime updatedat;

	// Fields for user's local time
	@Column(nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate userlocaldate;

	@Column(nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
	private LocalDateTime userlocaldatetime;

	@Column(nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
	private LocalDateTime userlocalupdatedtime;

	@PrePersist
	public void prePersist() {
		if (createddate == null) {
			createddate = LocalDate.now(); // Set the current server date
		}
		if (createdtime == null) {
			createdtime = LocalDateTime.now(); // Set the current server datetime
		}
	}
}
