package com.voicemate.usermanagementservice.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserPojo {

	private Long id;
	private String email;
	private String name;
	private String password;
	private String oauth2provider;
	private String oauth2providerid;
	private boolean is2faenabled;
	private String phonenumber;
	private String twofactorauthtoken;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
}
